package pt.isel.g20.unicommunity.forumItem.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.annotations.CreationTimestamp
import pt.isel.g20.unicommunity.comment.model.Comment
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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    var comments: MutableList<Comment> = mutableListOf()

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", locale = "en_GB")
    @Column
    @CreationTimestamp
    var createdAt: Date? = null

    constructor() :this("", "", "Luis Vaz")

}

