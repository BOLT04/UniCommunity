import { asyncPostRequest } from '../common/common'

/**

 * Returns a Blackboard response object. Format:
 * {
 *    name: string,
 *    content: array<Item>
 * }
 * 
 * Item: 
 * {name: string, text: string}
 * 
 * @param {string} url Represents the URL of the HTTP request to be made
 */
export default async function fetchBlackboardAsync(url) {
    return fetch('http://localhost:8080'+url) //TODO: remove hardcoded
}

export async function asyncCreateBlackboardItem(url, name, content) {
    // Construct request body
    const body = { name, content }

    return await asyncPostRequest(url, body)
}