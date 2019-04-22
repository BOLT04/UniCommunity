package pt.isel.g20.unicommunity.forumItem.model

import pt.isel.g20.unicommunity.forum.model.Forum
import javax.persistence.*

@Entity
class ForumItem(
    @Column(nullable = false) var name: String,
    @Column(nullable = false) var content: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne
    var forum: Forum? = null

    constructor() :this("", "")
}

