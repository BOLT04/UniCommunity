package pt.isel.g20.unicommunity.forum.model

import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import javax.persistence.*

@Entity
class Forum(
        @Id
        var id: Long,

        var isAllowImagePosts: Boolean
) {

    @OneToOne
    var board: Board? = null

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    var items: MutableList<ForumItem> = mutableListOf()

    constructor() : this (0, false)

    constructor(id: Long): this(id, false)
}
