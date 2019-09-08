// This module contains functions that are used by multiple modules.
import { AUTH_TOKEN } from './constants'

/**
 * Returns an array with model objects that are representations of the UI. 
 * Each object has the format specified by the server
 * 
 * @param {array} items - items array with the format specified in the media type: Collections+json
 */
export function itemsToModelRepr(items) {
    return items.map(({ href, data, links }) => {
        const obj = { href, links }

        if (data)
            data.forEach(dataObj => {
                obj[dataObj.name] = dataObj.value
            })
        
        obj.getHrefOfRel = rel => {
            const link = links.find(link => link.rel === rel)
            return link ? link.href : null
        }
        obj.hasLinkRel = rel => {
            const link = links.find(link => link.rel === rel)
            return link ? true : false
        }

        return obj
    })
}

const config = require('../unicommunity-config.json')
const baseUri = `http://${config.serverHost}:${config.serverPort}`
export const baseClientUri = `http://${config.clientHost}:${config.clientPort}`

/**
 * Builds the uri to use in a fetch request.
 * @param {string} relativeUrl - The relative url to be prefixed by the base uri.
 */
export const buildUri = relativeUrl => `${baseUri}${relativeUrl}`

/**
 * Makes a GET HTTP request to the base URI configured in the project, appended with
 * the relative URL.
 * @param {string} relativeUrl - The relative url to be prefixed by the base uri.
 * @param {string} contentType - (optional) The content type value of the Accept header.
 */
export const asyncRelativeFetch = (relativeUrl, contentType) => {
    const options = fillAuthHeaderIfAuthenticated()
    if (contentType) {
        if (!options.headers) options.headers = {}

        options.headers['Accept'] = contentType
    }

    return fetch(buildUri(relativeUrl), options)
}

//TODO: these two function below will disappear after we use HAL+forms to specify how actions are made!
/**
 * Makes a Post HTTP request with the given body.
 * @param {string} relativeUrl - The relative url to be prefixed by the base uri.
 * @param {object} body - the Request body object
 */
export const asyncPostRequest = (relativeUrl, body) =>
    asyncRelativeHttpRequest(relativeUrl, 'post', 'application/json', body)

export const asyncPutRequest = (relativeUrl, body) =>
    asyncRelativeHttpRequest(relativeUrl, 'put', 'application/json', body)

export const asyncHalFormsRequest = (reqInfo, relativeUrl) => {
    const body = {}
    // Construct request body
    reqInfo.properties.forEach(p =>
        body[p.name] = p.value
    )

    return asyncRelativeHttpRequest(relativeUrl, reqInfo.method, reqInfo.contentType, body)
}
/*
export const asyncHalFormsWithoutBodyRequest = async (rel, relativeUrl) => {

     //TODO: maybe make a function that given a rel and href/relativeUrl, makes a halforms req to then use asyncRelativeHttpRequest
                    let rsp = await asyncRelativeFetch(rel)
                    //const { _templates: { default: reqInfo } } = await asyncParseHalFormRsp(rsp)
                    const { _templates: { default: reqInfo } } = await asyncRelativeFetch(rels.getBlackboards)
                            .then(asyncParseHalFormRsp)

                    return asyncRelativeHttpRequest(relativeUrl, reqInfo.method)
}*/

/**
 * Makes a Post HTTP request with the given body.
 * @param {string} relativeUrl - The relative url to be prefixed by the base uri.
 * @param {string} method      - The HTTP request method.
 * @param {string} contentType - (optional) The content type value of the Content-Type header.
 * @param {object} body        - (optional) the Request body object.
 */
export function asyncRelativeHttpRequest(relativeUrl, method, contentType, body) {
    let options = fillAuthHeaderIfAuthenticated()
    
    if (body && contentType) {
        // Fill headers
        if (!options.headers) options.headers = {}
        options.headers['Content-Type'] = contentType

        options.body = JSON.stringify(body)
    }
    options.method = method        

    return fetch(buildUri(relativeUrl), options)
}

// Auxiliary function
function fillAuthHeaderIfAuthenticated() {
    let options = {}
    const token = localStorage.getItem(AUTH_TOKEN)
    if (token)
        options.headers = {
            Authorization: `Basic ${token}`
        }
        
    return options
}

/**
 * This function removes any functions on an object.
 * We need this because history.push from the library React-Router doesn't support for the object
 * state to have functions.
 */
export const removeFunctionsFrom = obj => JSON.parse(JSON.stringify(obj))
