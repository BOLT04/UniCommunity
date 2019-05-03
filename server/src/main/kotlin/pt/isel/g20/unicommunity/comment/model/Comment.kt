package pt.isel.g20.unicommunity.comment.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.annotations.CreationTimestamp
import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import java.util.*
import javax.persistence.*

@Entity
class Comment(
        @ManyToOne var forumItem: ForumItem? = null,
        @Column(nullable = false) var content: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", locale = "en_GB")
    @Column
    @CreationTimestamp
    var createdAt: Date? = null

    constructor() :this(null,"")
}