import { APPLICATION_HAL_JSON } from '../../common/constants'

import relsRegistery from '../../common/rels-registery'
import { MappingError } from '../../common/errors'

import ForumItem from '../../components/pages/post_details/model/ForumItem'

/**
 * @throws {MappingError} - Throws this exception in case the Content-Type header isn't the correct one.
 * @returns {ForumItem}   - The forum item object that represents a post on the UI.
 * @param {object} rsp    - Represents the response of the API that comes in HAL+JSON format.
 */
export default async function rspToForumItemAsync(rsp) {
    const contentType = rsp.headers.get('Content-Type')
    
    if (contentType.includes(APPLICATION_HAL_JSON)) {
        // Sanity check. In the HTTP request we sent the header Accept, so we check if the server does support it.
        const { name, content, authorName, createdAt, ...body } = await rsp.json()
        let forumItemArgs = []
        forumItemArgs.push(name)
        forumItemArgs.push(content)
        forumItemArgs.push(authorName)
        forumItemArgs.push(createdAt)
      
        const links = {}
        if (body._links) {
            for (let prop in body._links)
                if (prop in relsRegistery)
                    links[relsRegistery[prop].propName] = body._links[prop].href
        }

        const forumItem = new ForumItem(...forumItemArgs)
        if (links)
            forumItem.links = links
        
        return forumItem
    }

    throw new MappingError()
}