// Maps a response of the endpoint 'Create a Board' to the model representation of a Board in the UI.

import {
    itemsToModelRepr,
    asyncRelativeFetch,
    asyncRelativeHttpRequest
} from '../../common/common'
import { APPLICATION_HAL_JSON, COLLECTION_JSON } from '../../common/constants'
import asyncCollectionRspToList from './collectionJson-mapper'
import asyncParseHalFormRsp from './halForms-mapper'

import relsRegistery, { rels } from '../../common/rels-registery'

import Board from '../../components/pages/board_details/model/Board'
import Blackboard from '../../components/pages/board_details/model/Blackboard'

import HypermediaObject from './HypermediaObject'
import { MappingError } from '../../common/errors'

import asyncBaseMapper from './base-mapper'

/**
 * @throws {MappingError} - Throws this exception in case the Content-Type header isn't the correct one.
 * @returns {Board}       - The board object that represents the UI.
 * @param {Response} rsp  - Represents the response of the API that comes in HAL+JSON format.
 */
export default async function rspToBoardAsync(rsp) {
    const parseRsp = async () => {
        // Sanity check. In the HTTP request we sent the header Accept, so we check if the server does support it.
        const { id, name, description, _links, _embedded } = await rsp.json()

        const board = new Board(id, name, description, {})
        let forumHref

        if (_links) {//TODO: i dont know what to do here...
            // Add methods to board object to simplify operations on the links array.
            const hypermediaObj = new HypermediaObject(_links)
            Object.setPrototypeOf(board, hypermediaObj)

            if ('self' in _links)
                board.serverHref = _links['self'].href
            /*
            Object.keys(body._links)
                .forEach(prop => {
                    const relObj = relsRegistery[prop]
                    if (relObj)
                        relObj.serverHref = body._links[prop].href
                })
            board.forumLinks = relsRegistery['/rels/forum']
            */
            const editBoardHref = board.getHrefOfRelHal(rels.editBoard)
            if (editBoardHref)
                board.editBoardHref = editBoardHref

            const addMemberToBoardHref = board.getHrefOfRelHal(rels.addMemberToBoard)

            if (addMemberToBoardHref)
                board.addMemberHref = addMemberToBoardHref
            
            const forumRel = rels.getForum
            if (_links[forumRel])
                forumHref = _links[forumRel].href
        }

        // Check if the blackboards rel is present
        if (_embedded) {
            const blackboardsArr = _embedded[rels.getBlackboards]
            if (blackboardsArr)
                board.modules.blackboards = await Promise.all(
                    blackboardsArr.map(async blackboard =>
                        await halItemToBlackboardAsync(board, blackboard)
                    )
                )
            else {
                const blackboardsHref = board.getHrefOfRelHal(rels.getBlackboards)
                if (blackboardsHref) {
                    //TODO: maybe make a function that given a rel and href/relativeUrl, makes a halforms req to then use asyncRelativeHttpRequest
                    // Then fetch and add the blackboards property to modules
                    let rsp = await asyncRelativeFetch(rels.getBlackboards)
                    const { _templates: { default: reqInfo } } = await asyncParseHalFormRsp(rsp)

                    rsp = await asyncRelativeHttpRequest(blackboardsHref, reqInfo.method)
                    const blackboardsArr = (await asyncCollectionRspToList(rsp)).items
                    board.modules.blackboards = await Promise.all(
                        blackboardsArr.map(async blackboard =>
                            await colItemToBlackboardAsync(board, blackboard)
                        )
                    )
                }
            }
        }

        if (forumHref)
            board.modules.forum = await fetchForumAsync(forumHref)
        
            return board
    }
//TODO: make a react component that handles errors that are thrown
    return asyncBaseMapper(rsp, APPLICATION_HAL_JSON, parseRsp)
}

/**
 * 
 * @param {object} board      - The board object
 * @param {object} blackboard - The collection+json item object
 */
async function colItemToBlackboardAsync(board, { href }) {
    const rsp = await asyncRelativeFetch(href, APPLICATION_HAL_JSON)
    const body = await rsp.json()
    return parseOutputModelToBlackboard(board, body)
}

async function halItemToBlackboardAsync(board, { _links }) {
    //TODO: what if the self link isnt there... we need to be prepared for that and put content = [] maybe
    const rsp = await asyncRelativeFetch(_links.self.href, APPLICATION_HAL_JSON)
    const body = await rsp.json()
    return parseOutputModelToBlackboard(board, body)
}

export async function parseOutputModelToBlackboard(board, body) {
    const {
        id,
        name,
        notificationLevel,
        description,
        _links: blackboardLinks
    } = body

    const itemsLink = blackboardLinks && blackboardLinks[rels.getBlackboardItems]
    let items = []
    if (itemsLink) {
        const rsp = await asyncRelativeFetch(itemsLink.href, COLLECTION_JSON)
        items = (await asyncCollectionRspToList(rsp)).items
    }
    const blackboard = new Blackboard(id, name, notificationLevel, description, items)
    const createItemLink = blackboardLinks[rels.createBlackboardItem]
    const createBlackboardItemHref = createItemLink && createItemLink.href
    const registery = relsRegistery[rels.createBlackboardItem]
    // If the response includes a link to create an item and its registered, add it to the object to return
    if (createBlackboardItemHref && registery)
        blackboard.createLink = {
            clientHref: registery.clientHref(board, blackboard),
            serverHref: createBlackboardItemHref
        }

    const editLink = blackboardLinks[rels.editBlackboard]
    const editBlackboardHref = editLink && editLink.href
    if (editBlackboardHref)
        blackboard.editLinkHref = editBlackboardHref

    return blackboard
}

/**
 * Returns an object with the format:
 * {
 *     posts: array,
 *     createPostHref: {
 *         clientHref: string,
 *         serverHref: string
 *     }
 * }
 * 
 * @param {string} forumHref 
 */
async function fetchForumAsync(forumHref) {
    //TODO: what if the self link isnt there... we need to be prepared for that and put content = [] maybe
    // Auxiliar function
    const getForumItemsHref = async () => {
        const rsp = await asyncRelativeFetch(forumHref, APPLICATION_HAL_JSON)
        const { _links } = await rsp.json()
        
        if (_links) {
            const forumItemsRel = rels.getForumItems
            if (_links[forumItemsRel] && relsRegistery[forumItemsRel])
                return _links[forumItemsRel].href
        } else return null
    }

    const forumItemsHref = await getForumItemsHref()
    if (!forumItemsHref) return null

    const rsp = await asyncRelativeFetch(forumItemsHref, COLLECTION_JSON)
    const { collection: { links, items } } = await rsp.json()

    //TODO: use asyncCollectionRspToList in collectionJson mapper since its the same code
    const posts = items.length === 0 ? [] : itemsToModelRepr(items)
    let createPostHrefObj
    // Check if the link to create a post exists
    links.forEach(l => {
        const rel = rels.createForumItem
        const registery = relsRegistery[rel]
        if (l.rel === rel && registery)
            createPostHrefObj = {
                clientHref: registery.clientHref,
                serverHref: l.href
            }
    })

    return { createPostHrefObj, posts }
}