'use strict'
// This module contains functions that are used by multiple modules.

/**
 * This function is used by the mapper functions - transforms a API response to a model object,
 * Returns an array with model objects that are representations of the UI. 
 * Each object has the format specified by the server
 * 
 * @param {array} items - items array with the format specified in the media type: Collections+json
 */
export function itemsToModelRepr(items) {
    return items.map(({ href, data, links }) => {
        const obj = { href }

        if (data)
            data.forEach(dataObj => {
                obj[dataObj.name] = dataObj.value
            })
        
        //TODO: use links

        return obj
    })
}

const config = require('../unicommunity-config.json')
const baseUri = `http://${config.serverHost}:${config.serverPort}`

/**
 * Builds the uri to use in a fetch request.
 * @param {string} relativeUrl - The relative url to be prefixed by the base uri.
 */
export function buildUri(relativeUrl) {
    return `${baseUri}${relativeUrl}`
}