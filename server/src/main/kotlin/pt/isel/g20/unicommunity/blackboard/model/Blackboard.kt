package pt.isel.g20.unicommunity.blackboard.model

import pt.isel.g20.unicommunity.blackboardItem.model.BlackboardItem
import pt.isel.g20.unicommunity.board.model.Board
import javax.persistence.*

@Entity
class Blackboard(
        @Column(nullable = false) var name: String,
        @Column var notificationLevel: String,
        @Column var description: String? = null,
        @ManyToOne(fetch = FetchType.LAZY) var board: Board? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    var items: MutableList<BlackboardItem> = mutableListOf()

    constructor() : this("", "", null, null)

    constructor(name: String,  notificationLevel: String, board: Board)
        : this(name, notificationLevel, null, board)
}