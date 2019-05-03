import { itemsToModelRepr } from '../../common/common'
import { COLLECTION_JSON } from '../../common/constants'

/**
 * This function ignores the links, queries and other parts of the media type. Its supposed to be
 * a very simple version of a mapper to only get the list of items inside the response.
 * @param {object} rsp Represents the response of the API that comes in vnd.collection+JSON format.
 */
export default async function asyncCollectionRspToList(rsp) {
    const contentType = rsp.headers.get('Content-Type')

    if (contentType.includes(COLLECTION_JSON)) {
        // Sanity check. In the HTTP request we sent the header Accept, so we check if the server does support it.
        let a = await rsp.json()
        debugger
        const { collection: { items } } = a

        return itemsToModelRepr(items)
    }

    return null // TODO: should I throw error instead?
}