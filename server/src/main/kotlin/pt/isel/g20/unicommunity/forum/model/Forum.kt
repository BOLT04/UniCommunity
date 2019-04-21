package pt.isel.g20.unicommunity.forum.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Forum {

    @Id
    var boardId: Long = 0

    var isAllowImagePosts: Boolean = false

    constructor() {}

    constructor(boardId: Long, allowImagePosts: Boolean = false) {
        this.boardId = boardId
        this.isAllowImagePosts = allowImagePosts
    }
}
