/**
 * A forum item represents a post on the forum of a board.
 * @param {string} name      - The name of the forum item.
 * @param {string} content   - The text describing the post.
 * @param {string} author    - The name of the author of this forum item.
 * @param {string} createdAt - The date that this post was created at.
 */
export default function ForumItem(name, content, author, createdAt) {
    this.name = name
    this.content = content
    this.author = author
    this.createdAt = createdAt
}