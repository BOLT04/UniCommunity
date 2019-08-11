import { APPLICATION_HAL_JSON } from '../../common/constants'

import relsRegistery, { rels } from '../../common/rels-registery'
import { MappingError } from '../../common/errors'

import ForumItem from '../../components/pages/post_details/model/ForumItem'

/**
 * @throws {MappingError} - Throws this exception in case the Content-Type header isn't the correct one.
 * @returns {ForumItem}   - The forum item object that represents a post on the UI.
 * @param {object} rsp    - Represents the response of the API that comes in HAL+JSON format.
 */
export default async function rspToForumItemAsync(rsp) {
    // TODO: also validate status code and other headers and info about the response!
    const contentType = rsp.headers.get('Content-Type')
    
    if (contentType.includes(APPLICATION_HAL_JSON)) {
        // Sanity check. In the HTTP request we sent the header Accept, so we check if the server does support it.
        const { name, content, authorName, createdAt, ...body } = await rsp.json()
//TODO: validate if the response indeed comes with all the intended data, or else throw an exception ResponseBodyException!
        let forumItemArgs = []
        forumItemArgs.push(name)
        forumItemArgs.push(content)
        forumItemArgs.push(authorName)
        forumItemArgs.push(createdAt)
      
        const links = {}
        if (body._links) {//TODO: i dont know what to do here...
            // Filter by the links know by the client (present in registery)
            // then map all to an object will the properties defined in the registry object
            // plus the URL included in the link of the body
            for (let prop in body._links)
                if (prop in relsRegistery)
                    links[relsRegistery[prop].propName] = body._links[prop].href
            
            /*
            links = Object
                .keys(body._links)
                .filter(prop => prop in relsRegistery) 
                .map(prop => {
                    const relObj = relsRegistery[prop]
                    return {
                        ...relObj,
                        serverHref: body._links[prop].href
                    }
                })*/
               
        }

        // Check if the comments rel is present
        if (body._embedded) {
            const commentsArr = body._embedded[rels.getComments]
            if (commentsArr) {}
                /*TODOcomments = await Promise.all(
                    commentsArr.map(async i => 
                        await halItemToBlackboardItemAsync(i)  
                    )
                )
                */
        }

        //TODO: could this creation of forumItem with ifs be cleaner?
        /*
        above we say (line 24)
        const forumItemAux= { }

        then line 34 = forumItemAux.links=....
        then line 51 = forumItemAux.comments=....

        now 
        return {
            ...new ForumItem(...forumItemargs)
            ...forumItemAux
        }
        The problem might be that this object is not constructed with ForumItem so instanceOf is false.... :(
        */
        const forumItem = new ForumItem(...forumItemArgs)
        //TODO:forumItem = {...forumItem, ...forumItemAux} maybe this????
        if (links)
            forumItem.links = links
        
        return forumItem
    }

    throw new MappingError()
}