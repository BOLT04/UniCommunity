'use strict'
import { itemsToModelRepr } from '../../common/common'
import { APPLICATION_HAL_JSON } from '../../common/constants'

import relsRegistery from '../../common/rels-registery'
import { MappingError } from '../../common/errors'

import ForumItem from '../../components/post/model/ForumItem'

/**
 * @throws {MappingError} - Throws this exception in case the Content-Type header isn't the correct one.
 * @returns {ForumItem}   - The forum item object that represents a post on the UI.
 * @param {object} rsp    - Represents the response of the API that comes in HAL+JSON format.
 */
export default async function rspToForumItemAsync(rsp) {
    const contentType = rsp.headers.get('Content-Type')
    
    if (contentType.includes(APPLICATION_HAL_JSON)) {
        // Sanity check. In the HTTP request we sent the header Accept, so we check if the server does support it.
        const { name, content, author, createdAt, ...body } = await rsp.json()

        let forumItemArgs = []
        forumItemArgs.push(name)
        forumItemArgs.push(content)
        forumItemArgs.push(author)
        forumItemArgs.push(createdAt)

        let boardLink
        //let createCommentLink
        //let getAllCommentLink
      
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
           const boardRel = '/rels/getBoard'
           if (body._links[boardRel] && relsRegistery[boardRel])
            boardLink = body._links[boardRel].href
        }

        //TODO: impl. this when the server supports embedded
        // Check if the comments rel is present
        if (body._embedded) {
             
        }

        return new ForumItem(...forumItemArgs)
    }

    throw new MappingError()
}