package pt.isel.g20.unicommunity.comment.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.annotations.CreationTimestamp
import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import pt.isel.g20.unicommunity.user.model.User
import java.util.*
import javax.persistence.*

@Entity
class Comment(
        @ManyToOne @JoinColumn var forumItem: ForumItem? = null,
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn var author: User? = null,
        @Column(nullable = false) var content: String,
        @Column(nullable = false) var anonymousComment: Boolean

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", locale = "en_GB")
    @Column
    @CreationTimestamp
    var createdAt: Date? = null

    constructor() :this(null,null, "", false)
}