package pt.isel.g20.unicommunity.forumItem.model

import org.hibernate.annotations.CreationTimestamp
import pt.isel.g20.unicommunity.forum.model.Forum
import java.util.*
import javax.persistence.*

@Entity
class ForumItem(
    @Column(nullable = false) var name: String,
    @Column(nullable = false) var content: String,
    @Column(nullable = false) var author: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne
    var forum: Forum? = null

    @Column
    @CreationTimestamp
    var createdAt: Date? = null

    constructor() :this("", "", "Luis Vaz")

}

