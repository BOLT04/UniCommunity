/**
 * @param {number} id          - The identifier of the board
 * @param {string} name        - The name of the board
 * @param {string} description - The description text of the board
 * @param {Object} modules     - A container object for the modules of the board. The object has the format:
 * {
 *     blackboards: Blackboard[],
 *     forum: array
 * }
 * Blackboard:
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
export default function Board(id, name, description, modules) {
    this.id = id
    this.name = name
    this.description = description
    this.modules = modules
}