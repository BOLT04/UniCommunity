'use strict'
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

/**
 * 
 * @param {*} url 
 * @param {*} title 
 * @param {*} content
 * @param {boolean} anonymousPost - Specifies whether or not this post is to be created anonymously.
 */
export async function createForumPostsAsync(url, title, content, anonymousPost) {
    console.log(`Making a request to ${url} to create a forum post`)
    // Construct request body
    const body = {
        name: title,
        content,
        anonymousPost
    }

    return fetch(url, {
        method: 'post',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    })
}

export const asyncFetch = async relativeUrl => fetch(buildUri(relativeUrl))