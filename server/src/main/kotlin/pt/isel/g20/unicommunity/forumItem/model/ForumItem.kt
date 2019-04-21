package pt.isel.g20.unicommunity.forumItem.model

import javax.persistence.*

@Entity
class ForumItem(
    var boardId: Long,
    @Column(nullable = false) var name: String,
    @Column(nullable = false) var content: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    constructor() :this(0, "", "")
}

