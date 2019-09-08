import { asyncPostRequest } from '../common/common'

export async function createForumPostsAsync(url, name, content, anonymousPost) {
    // Construct request body
    const body = {
        name,
        content,
        anonymousPost
    }

    return await asyncPostRequest(url, body)
}