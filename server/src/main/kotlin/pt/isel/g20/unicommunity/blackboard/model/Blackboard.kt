package pt.isel.g20.unicommunity.blackboard.model

import com.fasterxml.jackson.annotation.JsonIgnore
import pt.isel.g20.unicommunity.blackboardItem.model.BlackboardItem
import pt.isel.g20.unicommunity.board.model.Board
import javax.persistence.*

@Entity
class Blackboard(
        @Column(nullable = false) var name: String,
        @Column var notificationLevel: String,
        @Column var description: String? = null,
        @JsonIgnore @ManyToOne(fetch = FetchType.LAZY) var board: Board
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    var items: MutableList<BlackboardItem> = mutableListOf()
}