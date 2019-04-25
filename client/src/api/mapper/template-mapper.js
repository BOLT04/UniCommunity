'use strict'
import { itemsToModelRepr } from '../../common/common'
import { COLLECTION_JSON } from '../../common/constants'
/**
 * 
 * @param {object} rsp Represents the response of the API that comes in HAL+JSON format.
 */
export default async function rspToTemplatesAsync(rsp) {
    const contentType = rsp.headers.get('Content-Type')

    if (contentType.includes(COLLECTION_JSON)) {
        // Sanity check. In the HTTP request we sent the header Accept, so we check if the server does support it.
        const { collection: { items } } = await rsp.json()

        let templates = itemsToModelRepr(items)

        // Transform the string of names into an array of names
        templates = templates.map(template => ({
            ...template,
            blackboardNames: template.blackboardNames.split(',')
        }))

        return templates
    }

    return null // TODO: should I throw error instead?
}