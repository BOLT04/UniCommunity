/**
 * @param {number} id                - The identifier of this blackboard resource.
 * @param {string} name
 * @param {string} notificationLevel - The level that specifies when the user wants to receive notifications
 * of new content for this board.
 * @param {string} description       - The description text of the blackboard
 * @param {array} items              - An array of blackboard items representing the content of the blackboard.
 */
export default function Blackboard(id, name, notificationLevel, description, items) {
    this.id                = id
    this.name              = name
    this.notificationLevel = notificationLevel
    this.description       = description
    this.items             = items
}