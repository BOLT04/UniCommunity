export default class HypermediaObject {
    constructor(links) {
        this.links = links
    }

    getHrefOfRelHal(rel) {
        const link = this.links[rel]
        return link ? link.href : null
    }
}