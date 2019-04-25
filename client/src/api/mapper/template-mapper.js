'use strict'
import { itemsToModelRepr } from '../../common/common'

/**
 * 
 * @param {object} rsp Represents the response of the API that comes in HAL+JSON format.
 */
export default async function rspToTemplatesAsync(rsp) {//TODO: maybe move these constants strings to another file. Like how its done on the server, MimeType class?
    const contentType = rsp.headers.get('Content-Type')

    if (contentType === 'application/vnd.collection+json') {
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