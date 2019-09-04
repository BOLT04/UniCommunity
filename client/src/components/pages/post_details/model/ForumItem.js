/**
 * A forum item represents a post on the forum of a board.
 * @param {string} name       - The name of the forum item.
 * @param {string} content    - The text describing the post.
 * @param {string} authorName - The name of the author of this forum item.
 * @param {string} createdAt  - The date that this post was created at.
 * @param {object[]} comments - The comments associated to this forum item.
 */
export default function ForumItem(name, content, authorName, createdAt, comments = []) {
    this.name       = name
    this.content    = content
    this.authorName = authorName
    this.createdAt  = createdAt
    this.comments   = comments
}