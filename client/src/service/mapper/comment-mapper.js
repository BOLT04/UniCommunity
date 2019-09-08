import Comment from '../../components/pages/post_details/model/CommentModel'
import { APPLICATION_HAL_JSON } from '../../common/constants'

import asyncBaseMapper from './base-mapper'

/**
 * @returns {Comment}   - The comment object that represents a comment on the UI.
 * @param {object} rsp  - Represents the response of the API that comes in HAL+JSON format.
 */
export default async function asyncRspToComment(rsp) {
    const parseRsp = async () => {
        const { id, avatarSrc, author, createdAt, content, ...body } = await rsp.json()

        const commentArgs = [
            id,
            avatarSrc,
            author,
            createdAt,
            content
        ]

        const comment = new Comment(...commentArgs)

        if (body._links)
            comment.href = body._links.self && body._links.self.href
        
        comment.links = body._links

        return comment
    }

    return asyncBaseMapper(rsp, APPLICATION_HAL_JSON, parseRsp)
}