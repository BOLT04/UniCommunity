/**
 * @param {string} name - The name of the board
 * @param {string} description - The description text of the board
 * @param {Object[]} modules - The modules array of the board. Each modules is an object with the format:
 * {
 *     name: string,
 *     content: array<Item>
 * }
 * 
 * Item has the format:
 * {
 *     name: string,
 *     text: string
 * }
 */
export default function Board(name, description, modules, forumLinks) {
    this.name = name
    this.description = description
    this.modules = modules
    this.forumLinks = forumLinks
}