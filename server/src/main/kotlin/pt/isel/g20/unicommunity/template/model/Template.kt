package pt.isel.g20.unicommunity.template.model

import pt.isel.g20.unicommunity.board.model.Board
import javax.persistence.*

@Entity(name = "templates")
class Template(
        @Column(nullable = false) var name: String,
        @Column var hasForum: Boolean,
        @Column var blackboardNames: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}