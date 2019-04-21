// This module contains functions that are used by multiple mappers.

/**
 * Returns an array with model objects that are representations of the UI. 
 * Each object has the format specified by the server
 * 
 * @param {array} items - items array with the format specified in the media type: Collections+json
 */
export function itemsToModelRepr(items) {
    return items.map(({ href, data }) => {
        const obj = { href }

        data.forEach(dataObj => {
            obj[dataObj.name] = dataObj.value
        })

        return obj
    })
}