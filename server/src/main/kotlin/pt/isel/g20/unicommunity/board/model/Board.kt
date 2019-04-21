package pt.isel.g20.unicommunity.board.model

import javax.persistence.*

@Entity
class Board(
    @Column(nullable = false) var name: String,
    @Column var templateId: Long,
    @Column var description: String? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    constructor() : this("", 0, null)
}
