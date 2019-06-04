import Comment from '../../components/post/model/CommentModel'

/**
 * @returns {Comment}   - The forum item object that represents a post on the UI.
 * @param {object} rsp    - Represents the response of the API that comes in HAL+JSON format.
 */
export default async function asyncRspToComment(rsp) {
    const contentType = rsp.headers.get('Content-Type')
    //TODO: this code is repeated in prject-mapper!

    if (contentType.includes('application/hal+json')) {
        // Sanity check. In the HTTP request we sent the header Accept, so we check if the server does support it.
        const { id, avatarSrc, author, createdAt, content, ...body } = await rsp.json()

        let commentArgs = []
        commentArgs.push(id)
        commentArgs.push(avatarSrc)
        commentArgs.push(author)
        commentArgs.push(createdAt)
        commentArgs.push(content)

        const comment = new Comment(...commentArgs)

        if (body._links)
            //TODO: what if self doesnt come!! we need to handle this 'undefined' case
            comment.href = body._links.self && body._links.self.href
        
        comment.links = body._links

        return comment
    }
}