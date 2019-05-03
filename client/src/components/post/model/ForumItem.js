/**
 * A forum item represents a post on the forum of a board.
 * @param {string} name      - The name of the forum item.
 * @param {string} content   - The text describing the post.
 * @param {string} author    - The name of the author of this forum item.
 * @param {string} createdAt - The date that this post was created at.
 */
export default function ForumItem(name, content, author, createdAt) {
    this.name      = name
    this.content   = content
    this.author    = author
    this.createdAt = createdAt
}
// TODO: there is a problem putting links and comments in the constructor, since they can just not be in the
//TODO: response of the API, therefore undefined, passing that to the constructor doesn't seem correct..
//TODO: also if it doesnt have any links but it has embedded objects, then calling the constructor is difficult
//since links comes first. So a better option, instead of checking for undefined or null in the ctor, we do that
//outside, meaning the mapper functions add these properties (links, embedded) to the ForumItem object if they exist

/**
 * @param {Object} links     - The links object containing all the URLs parsed from a response of the API.
 * The format is the property
 */
/*
export default function ForumItem(name, content, author, createdAt, links, comments) {
    this.name      = name
    this.content   = content
    this.author    = author
    this.createdAt = createdAt
    this.links     = links
    this.comments  = comments
}*/