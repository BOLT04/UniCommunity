package pt.isel.g20.unicommunity.forum.model

import pt.isel.g20.unicommunity.board.model.Board
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class Forum {

    @Id
    var id: Long = 0

    var isAllowImagePosts: Boolean = false

    @OneToOne
    var board: Board? = null

    constructor() {}

    constructor(id: Long, allowImagePosts: Boolean = false) {
        this.id = id
        this.isAllowImagePosts = allowImagePosts
    }
}
