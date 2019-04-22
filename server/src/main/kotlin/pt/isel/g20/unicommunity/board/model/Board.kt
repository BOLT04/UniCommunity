package pt.isel.g20.unicommunity.board.model

import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.forum.model.Forum
import javax.persistence.*

@Entity
class Board(
    @Column(nullable = false) var name: String,
    @Column var templateId: Long?,// TODO: this is probably not necessary
    @Column var description: String? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToOne
    @JoinColumn
    var forum: Forum? = null

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    val blackBoards: MutableList<Blackboard> = mutableListOf()

    constructor() : this("", 0, null)
    constructor(name: String, templateId: Long) : this(name, templateId, null)
}
