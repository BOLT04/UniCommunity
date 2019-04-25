'use strict'
// Maps a response of the endpoint 'Create a Board' to the model representation of a Board in the UI.

//todo: REMOVE MOCKS
//import fetchBlackboardAsync from '../BlackBoardApi'
//import fetchForumPostsAsync from '../ForumApi'
import fetchForumPostsAsync from '../ForumApiImpl'
import fetchBlackboardAsync from '../BlackBoardApiImpl'

import { itemsToModelRepr } from '../../common/common'

import relsRegistery from '../../common/rels-registery'

import Board from '../../components/board_details/model/Board'

/**
 * @returns {Board}    The board object that represents the UI.
 * @param {Response} rsp Represents the response of the API that comes in HAL+JSON format.
 */
export default async function rspToBoardAsync(rsp) {//TODO: maybe move these constants strings to another file. Like how its done on the server
    const contentType = rsp.headers.get('Content-Type')
    let board = {}
    
    if (contentType.includes('application/hal+json')) {
        // Sanity check. In the HTTP request we sent the header Accept, so we check if the server does support it.
        const body = await rsp.json()

        board.name = body.name
        board.description = body.description
        board.modules = { }

        let forumLink
      
        if (body._links) {//TODO: i dont know what to do here...
            /*
            Object.keys(body._links)
                .forEach(prop => {
                    const relObj = relsRegistery[prop]
                    if (relObj)
                        relObj.serverHref = body._links[prop].href
                })
            board.forumLinks = relsRegistery['/rels/forum']
            */
           const forumRel = 'http://localhost:8080/rels/getForumItems'// TODO: can this be hardcoded here?
           if (body._links[forumRel] && relsRegistery['/rels/getForumItems']) //TODO: fix hardcoded relsRegistery[forumRel]
               forumLink = body._links[forumRel].href
        }

        // Check if the blackboards rel is present
        if (body._embedded) {
            const blackboardsArr = body._embedded['http://localhost:8080/rels/getBlackboards']
            if (blackboardsArr)
                board.modules.blackboards = await Promise.all(
                    blackboardsArr.map(async i => 
                        await halItemToBlackboardItemAsync(i)  
                    )
                )   
        }

        // Make a request to get the forum and add it to the modules array
        board.modules.forum = await fetchForumAsync(forumLink)
    }

    return board
}

async function halItemToBlackboardItemAsync({ name, _links }) {
    //TODO: what if the self link isnt there... we need to be prepared for that and put content = [] maybe
    const rsp =  await fetchBlackboardAsync(_links.self.href)
    const { items, _links: blackboardLinks } = await rsp.json()

    const blackboardItem = { name, items }

    const serverHref = blackboardLinks['http://localhost:8080/rels/createBlackboardItem']
    const registery = relsRegistery['/rels/createBlackboardItem']// todo: http://localhost:8080
    // If the response includes a link to create an item and its registered, add it to the object to return
    if (serverHref && registery) 
        blackboardItem.createLink = {
            clientHref: registery.clientHref,
            serverHref
        }

    return blackboardItem
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
    const rsp =  await fetchForumPostsAsync(forumLink)
    const { collection: { links, items } } = await rsp.json()

    debugger
    const posts = items.length == 0 ? [] : itemsToModelRepr(items)
    debugger
    let createPostHrefObj
    // Check if the link to create a post exists
    links.forEach(l => {
        const rel = 'http://localhost:8080/rels/createForumItem'
        const registery = relsRegistery[rel]
        if (l.rel === rel && registery)
            createPostHrefObj = {
                clientHref: registery.clientHref,
                serverHref: l.href
            }
    })

    return { createPostHrefObj, posts }
}