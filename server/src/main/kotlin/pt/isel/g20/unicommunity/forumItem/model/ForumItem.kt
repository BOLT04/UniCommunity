package pt.isel.g20.unicommunity.forumItem.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.annotations.CreationTimestamp
import pt.isel.g20.unicommunity.comment.model.Comment
import pt.isel.g20.unicommunity.forum.model.Forum
import pt.isel.g20.unicommunity.user.model.User
import java.util.*
import javax.persistence.*

@Entity
class ForumItem(
        @ManyToOne var forum: Forum,
        @ManyToOne @JoinColumn var author: User,
        @Column(nullable = false) var name: String,
        @Column(nullable = false) var content: String,
        @Column(nullable = false) var anonymousPost: Boolean
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(fetch = FetchType.LAZY)
    var comments: MutableList<Comment> = mutableListOf()

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", locale = "en_GB")
    @Column
    @CreationTimestamp
    var createdAt: Date? = null
}

