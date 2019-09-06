package pt.isel.g20.unicommunity.board.model

import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.forum.model.Forum
import javax.persistence.*

@Entity
class Board(
    @Column(nullable = false) var name: String,

    // TODO: Devia criar a relacao com o template e ter aqui uma referencia para o template?
    @Column var templateId: Long?,

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
