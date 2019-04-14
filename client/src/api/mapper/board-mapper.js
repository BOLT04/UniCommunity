// Maps a response of the endpoint 'Create a Board' to the model representation of a Board in the UI.

import fetchBlackboardAsync from '../BlackBoardApi'

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

        if (body._links) {
            //todo: this
        }

        // Check if the blackboards rel is present
        if (body._embedded) {
            const blackboardsArr = body._embedded['http://localhost:8080/rels/blackboards']
            if (blackboardsArr)
            //TODO: right now modules array only has the blackboards
                board.modules = await Promise.all(
                    blackboardsArr.map(async i => 
                        await halItemToBlackboardItem(i)  
                    )
                )   
        }
    }

    return board
}

async function halItemToBlackboardItem({ name, _links }) {
    //TODO: what if the self link isnt there... we need to be prepared for that and put content = [] maybe
    const { content } =  await fetchBlackboardAsync(_links.self.href)

    return { name, content }
}