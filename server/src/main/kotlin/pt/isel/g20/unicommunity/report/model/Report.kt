package pt.isel.g20.unicommunity.report.model

import pt.isel.g20.unicommunity.comment.model.Comment
import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import pt.isel.g20.unicommunity.user.model.User
import javax.persistence.*

@Entity(name = "reports")
class Report(

        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="userId") var user: User,

        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="forumItemId") var forumItem: ForumItem? = null,

        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="commentId") var comment: Comment? = null,

        @Column(nullable = false)
        var numberOfReports: Long
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}