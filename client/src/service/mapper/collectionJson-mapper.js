import { itemsToModelRepr } from '../../common/common'
import { COLLECTION_JSON } from '../../common/constants'

import asyncBaseMapper from './base-mapper'

/**
 * This function ignores the links, queries and other parts of the media type. Its supposed to be
 * a very simple version of a mapper to only get the list of items inside the response.
 * @param {object} rsp Represents the response of the API that comes in vnd.collection+JSON format.
 */
export default async function asyncCollectionRspToList(rsp) {
    const parseRsp = async () => {
        let body = await rsp.json()

        const { collection: { items } } = body
        
        // These functions are usefull in any object that has links
        body.collection.getHrefOfRel = function (rel) {
            const link = this.links.find(link => link.rel === rel)
            return link ? link.href : null
        }
        body.collection.hasLinkRel = function (rel) {
            const link = this.links.find(link => link.rel === rel)
            return link ? true : false
        }
        body.collection.buildUriFromQueryRel = function (rel, params) {
            const query = this.queries.find(query => query.rel === rel)
            return parseQueryTemplate(query, params)
        }
        
        return {
            ...body.collection,
            items: itemsToModelRepr(items)
        }
    }

    return asyncBaseMapper(rsp, COLLECTION_JSON, parseRsp)
}

/**
 * So that order of params doesn't matter (this is to decouple the client and server even futher), the params
 * array has objects that specify the name of the param. This protects against the eventuality of the server re-arranging
 * the order of the data array, present in the collection+JSON representation.
 * @param {object} query 
 * @param {array} params 
 * 
 * @returns string - The constructed URI from the query information
 */
function parseQueryTemplate(query, params) {
    let queryString = ''
    for (const [i, data] of query.data.entries()) {
        const p = params.find(p => p.name === data.name)
        if (i > 0)
            queryString += '&'
        queryString += `${p.name}=${p.value}`
    }

    return `${query.href}?${queryString}`
}