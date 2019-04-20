import React from 'react'
import Forum from '../../components/board_details/Forum'
// Maps a response of the endpoint 'Create a Board' to the model representation of a Board in the UI.

import fetchBlackboardAsync from '../BlackBoardApi'
import fetchForumPostsAsync from '../ForumApi'

import { itemsToModelRepr } from './common'

import relsRegistery from '../../common/rels-registery'

/**
 * 
 * @param {object} rsp Represents the response of the API that comes in HAL+JSON format.
 */
export default async function rspToBoardAsync(rsp) {//TODO: maybe move these constants strings to another file. Like how its done on the server
    const contentType = rsp.headers.get('Content-Type')
    let board = {}

    if (contentType === 'application/hal+json') {
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
           const forumRel = '/rels/forum'
           if (body._links[forumRel] && relsRegistery[forumRel]) 
               forumLink = body._links[forumRel].href
           
        }

        // Check if the blackboards rel is present
        if (body._embedded) {
            const blackboardsArr = body._embedded['http://localhost:8080/rels/blackboards']
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
    const { content, _links: blackboardLinks } =  await fetchBlackboardAsync(_links.self.href)

    const blackboardItem = { name, content }

    const serverHref = blackboardLinks['/rels/createBlackboardItem']
    const registery = relsRegistery['/rels/createBlackboardItem']
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
    const { collection: { links, items } } =  await fetchForumPostsAsync(forumLink)

    const posts = itemsToModelRepr(items)

    let createPostHref
    // Check if the link to create a post exists
    links.forEach(l => {
        const registery = relsRegistery['/rels/createPost']
        if (l.rel === '/rels/createPost' && registery)
            createPostHref = {
                clientHref: registery.clientHref,
                serverHref: l.href
            }
    })

    return { createPostHref, posts }
}