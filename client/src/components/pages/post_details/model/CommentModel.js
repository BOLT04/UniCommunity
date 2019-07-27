/**
 * @param {string} id        - The identifier of the comment
 * @param {string} avatarSrc - The relative URL for the image that represents the avatar of the author
 * @param {string} author    - The name of the author
 * @param {string} createdAt - The creation date of the comment
 * @param {string} content   - The text of the comment 
 */
export default function Comment(id, avatarSrc, author, createdAt, content) {
    this.id = id
    this.avatarSrc = avatarSrc
    this.author = author
    this.createdAt = createdAt
    this.content = content
}