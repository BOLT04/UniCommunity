package pt.isel.g20.unicommunity.board.model

import javax.persistence.*

@Entity
class Board(
    @Column(nullable = false) var name: String,
    @Column var description: String?
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    constructor() : this("", null)

    constructor(id: Long?, name: String, description: String) : this(name, description) {
        this.id = id!!
    }

    constructor(name: String) : this(name, null)
}
