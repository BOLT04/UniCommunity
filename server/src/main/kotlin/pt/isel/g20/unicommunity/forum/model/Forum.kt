package pt.isel.g20.unicommunity.forum.model

import com.fasterxml.jackson.annotation.JsonIgnore
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import javax.persistence.*

@Entity
class Forum(
        @JsonIgnore @OneToOne(fetch = FetchType.LAZY) @MapsId var board: Board
) {
    @Id
    @Column
    var id: Long = 0

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], mappedBy = "forum")
    var items: MutableList<ForumItem> = mutableListOf()
}
