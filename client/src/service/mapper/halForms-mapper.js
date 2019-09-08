import { APPLICATION_HAL_FORMS_JSON } from '../../common/constants'
import { MappingError } from '../../common/errors'

/**
 * @throws {MappingError} - Throws this exception in case the Content-Type header isn't the correct one.
 * {@link https://rwcbook.github.io/hal-forms/ HAL+FORMS media type}
 * 
 * @param {object} rsp Represents the response of the API that comes in application/prs.hal-forms+json format.
 */
export default async function asyncParseHalFormRsp(rsp) {
    const contentType = rsp.headers.get('Content-Type')

    if (contentType.includes(APPLICATION_HAL_FORMS_JSON)) {
        // Sanity check. In the HTTP request we sent the header Accept, so we check if the server does support it.
        return await rsp.json()
    }

    throw new MappingError()
}