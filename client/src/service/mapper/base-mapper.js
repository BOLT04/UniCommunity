import { APPLICATION_PROBLEM_JSON } from '../../common/constants'

import { MappingError } from '../../common/errors'

/**
 * Handles the case of the content type of the response being problem+json and checks if the content type
 * is the expected one.
 * @throws {MappingError}      - Throws this exception in case the Content-Type header isn't the correct one.
 * @returns {}                 - The return value of the parameter 'parseRsp'.
 * @param {Response} rsp       - Represents the response of the API that comes in HAL+JSON format.
 * @param {Function} parseRsp  - Maps the response payload to a model object.
 * The descriptor of this function is: () -> Promise<Object>
 */
export default async function asyncBaseMapper(rsp, expectedContentType, parseRsp) {
    const contentType = rsp.headers.get('Content-Type')

    if (contentType.includes(expectedContentType)) {
        // Sanity check. In the HTTP request we sent the header Accept, so we check if the server does support it.
        return await parseRsp()
    } else if (contentType.includes(APPLICATION_PROBLEM_JSON)) {
        // In this case the payload of the response has information about the error
        const error = await rsp.json()
        throw error
    }

    throw new MappingError()
}
