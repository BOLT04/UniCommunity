package pt.isel.g20.unicommunity.blackboard.model

import pt.isel.g20.unicommunity.board.model.Board
import javax.persistence.*

@Entity
class Blackboard(
        var boardId: Long,
        @Column(nullable = false) var name: String,
        @Column var notificationLevel: String,
        @Column var description: String? = null) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    var board: Board? = null

    constructor() : this(0, "", "", null)

    constructor(boardId: Long, id: Long, name: String, description: String, notificationLevel: String)
        : this(boardId, name, notificationLevel, description) {

        this.id = id
    }
}