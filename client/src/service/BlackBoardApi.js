import { asyncPostRequest } from '../common/common'

export async function asyncCreateBlackboardItem(url, name, content) {
    // Construct request body
    const body = { name, content }

    return await asyncPostRequest(url, body)
}