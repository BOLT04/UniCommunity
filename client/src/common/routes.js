const routes = {
    home: '/',
    boards: '/boards',
    getBoardUri(id) { 
        return `${this.boards}/${id}`
    },
    getPostDetailsUri(boardId, postId) {
        const boardUri = this.getBoardUri(boardId)
        return `${boardUri}/posts/${postId}`
    }
    
}

export default routes