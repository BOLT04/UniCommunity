const routes = {
    home: '/',
    login: '/login',
    boards: '/boards',
    getBoardUri(id) { 
        return `${this.boards}/${id}`
    },
    getPostDetailsUri(boardId, postId) {
        const boardUri = this.getBoardUri(boardId)
        return `${boardUri}/posts/${postId}`
    },
    /**
     * @param {object} board - Represents a Board (model data type) object.
     * @param {object} bb    - Represents a Blackboard (model data type) object.
     */
    getNewBlackboardItemUri(board, bb) {
        return `${board.id}/${bb.name}/blackboardItem/new`
    },
    getUserProfileUri(userId) {
        return `/user/${userId}`
    },
}

export default routes