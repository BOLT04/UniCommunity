package pt.isel.g20.unicommunity.board.model

import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.forum.model.Forum
import javax.persistence.*

@Entity
class Board(
    @Column(nullable = false) var name: String,
    @Column var templateId: Long?,// TODO: this is probably not necessary
    @Column var description: String? = null,

    @OneToOne
    @JoinColumn
    var forum: Forum?,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    val blackBoards: MutableList<Blackboard>
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    constructor() : this("", 0, null, null, mutableListOf())
}
