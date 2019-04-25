import { buildUri } from '../common/common'

/**
 * Returns an array of Small Forum Posts response object. Format:
 * {
 *    title: string,
 *    smallDesc: string,
 *    author: string,
 *    createdAt: string
 * }
 * 
 * @param {string} url Represents the URL of the HTTP request to be made
 */
export default async function fetchForumPostsAsync(url) {
    return fetch('http://localhost:8080'+url)
}

export async function createForumPostsAsync(url, title, content) {
    console.log(`Making a request to ${url} to create a forum post`)
    // Construct request body
    const body = {
        name: title,
        content
    }

    return fetch(url, {
        method: 'post',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    })
}

export async function getForumPostAsync(relativeUrl) {
    return fetch(buildUri(relativeUrl))
}