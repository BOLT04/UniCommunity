package pt.isel.g20.unicommunity.user.model

import com.fasterxml.jackson.annotation.JsonIgnore
import pt.isel.g20.unicommunity.blackboardItem.model.BlackboardItem
import pt.isel.g20.unicommunity.comment.model.Comment
import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import pt.isel.g20.unicommunity.report.model.Report
import pt.isel.g20.unicommunity.usersBlackboards.model.UsersBlackboards
import pt.isel.g20.unicommunity.usersBoards.UsersBoards
import javax.persistence.*

@Entity(name = "users")
class User(
        @Column(nullable = false)
        var name: String,

        @Column(nullable = false, unique = true)
        var email: String,

        @Column(nullable = false)
        var pw: String,

        @Column(nullable = false)
        var role: String,

        @Column var githubId: String? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val usersBoards : MutableList<UsersBoards> = mutableListOf()

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    val blackboardsSettings : MutableList<UsersBlackboards> = mutableListOf()

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = [CascadeType.REMOVE])
    val comments: MutableList<Comment> = mutableListOf()

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = [CascadeType.REMOVE])
    val forumItems: MutableList<ForumItem> = mutableListOf()

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = [CascadeType.REMOVE])
    val bbItems: MutableList<BlackboardItem> = mutableListOf()

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE])
    val reports: MutableList<Report> = mutableListOf()

    fun getBoards() = usersBoards.map { it.board }
}
