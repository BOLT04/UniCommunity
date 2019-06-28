// Maps a response of the endpoint 'Create a Board' to the model representation of a Board in the UI.

//todo: REMOVE MOCKS
//import fetchBlackboardAsync from '../BlackBoardApi'
//import fetchForumPostsAsync from '../ForumApi'
import fetchForumPostsAsync from '../ForumApiImpl'
import fetchBlackboardAsync from '../BlackBoardApiImpl'

import { itemsToModelRepr, asyncRelativeFetch } from '../../common/common'
import { APPLICATION_HAL_JSON, COLLECTION_JSON } from '../../common/constants'

import relsRegistery, { rels } from '../../common/rels-registery'

import Board from '../../components/board_details/model/Board'

import { MappingError } from '../../common/errors'

/**
 * @throws {MappingError} - Throws this exception in case the Content-Type header isn't the correct one.
 * @returns {Board}       - The board object that represents the UI.
 * @param {Response} rsp  - Represents the response of the API that comes in HAL+JSON format.
 */
export default async function rspToBoardAsync(rsp) {//TODO: maybe move these constants strings to another file. Like how its done on the server
    const contentType = rsp.headers.get('Content-Type')
    
    if (contentType.includes(APPLICATION_HAL_JSON)) {
        // Sanity check. In the HTTP request we sent the header Accept, so we check if the server does support it.
        const { name, description, _links, _embedded } = await rsp.json()

        let board = {
            name,
            description,
            modules: {}
        }

        let forumLink
      
        if (_links) {//TODO: i dont know what to do here...
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
           const forumRel = rels.getForumItems// TODO: can this be hardcoded here?
           if (_links[forumRel] && relsRegistery[rels.getForumItems]) //TODO: fix hardcoded relsRegistery[forumRel]
               forumLink = _links[forumRel].href
        }

        //TODO: REMOVE WHEN SERVER ADDS ID TO OUTPUT MODEL
        board.id = 10

        // Check if the blackboards rel is present
        if (_embedded) {
            const blackboardsArr = _embedded[rels.getBlackboards]
            if (blackboardsArr)
                board.modules.blackboards = await Promise.all(
                    blackboardsArr.map(async blackboard => 
                        await halItemToBlackboardAsync(board, blackboard)  
                    )
                )   
        }

        // Make a request to get the forum and add it to the modules array
        board.modules.forum = await fetchForumAsync(forumLink)

        return board
    }

	//TODO: add check of Problem+json! and throw an error perhaps
    throw new MappingError()
}

async function halItemToBlackboardAsync(board, { name, _links }) {
    //TODO: what if the self link isnt there... we need to be prepared for that and put content = [] maybe
    const rsp = await asyncRelativeFetch(_links.self.href, APPLICATION_HAL_JSON)
    const a = await rsp.json()
    const { items, _links: blackboardLinks } = a

    const blackboard = { name, items }
debugger
    const serverHref = blackboardLinks[rels.createBlackboardItem].href
    const registery = relsRegistery[rels.createBlackboardItem]
    // If the response includes a link to create an item and its registered, add it to the object to return
    if (serverHref && registery) 
        blackboard.createLink = {
            clientHref: registery.clientHref(board, blackboard),
            serverHref
        }

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
 * @param {string} forumLink 
 */
async function fetchForumAsync(forumLink) {
    //TODO: what if the self link isnt there... we need to be prepared for that and put content = [] maybe
    const rsp =  await asyncRelativeFetch(forumLink, COLLECTION_JSON)

    const { collection: { links, items } } = await rsp.json()

    //TODO: use asyncCollectionRspToList in collectionJson mapper since its the same code
    const posts = items.length == 0 ? [] : itemsToModelRepr(items)
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