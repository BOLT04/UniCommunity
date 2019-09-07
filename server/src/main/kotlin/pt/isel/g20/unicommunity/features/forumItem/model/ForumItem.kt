package pt.isel.g20.unicommunity.features.forumItem.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import pt.isel.g20.unicommunity.features.comment.model.Comment
import pt.isel.g20.unicommunity.features.forum.model.Forum
import pt.isel.g20.unicommunity.features.report.model.Report
import pt.isel.g20.unicommunity.features.user.model.User
import java.util.*
import javax.persistence.*

@Entity
class ForumItem(
        @ManyToOne @JoinColumn(name="forumId") var forum: Forum,
        @ManyToOne @JoinColumn var author: User,
        @Column(nullable = false) var name: String,
        @Column(nullable = false) var content: String,
        @Column(nullable = false) var anonymousPost: Boolean
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], mappedBy = "forumItem")
    var comments: MutableList<Comment> = mutableListOf()

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "forumItem", cascade = [CascadeType.REMOVE])
    val reports: MutableList<Report> = mutableListOf()

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss", locale = "en_GB")
    @Column
    @CreationTimestamp
    var createdAt: Date? = null
}

