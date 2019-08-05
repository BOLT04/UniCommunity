package pt.isel.g20.unicommunity.forum.model

import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import javax.persistence.*

@Entity
class Forum(
        @Id
        var id: Long,
        @OneToOne var board: Board
) {
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    var items: MutableList<ForumItem> = mutableListOf()
}
