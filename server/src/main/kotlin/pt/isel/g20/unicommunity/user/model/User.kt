package pt.isel.g20.unicommunity.user.model

import com.fasterxml.jackson.annotation.JsonIgnore
import pt.isel.g20.unicommunity.blackboardItem.model.BlackboardItem
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.comment.model.Comment
import pt.isel.g20.unicommunity.fcm.UserModuleConfig
import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import javax.persistence.*
import javax.persistence.ManyToMany



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
    @ManyToMany(mappedBy = "members")
    val boards : MutableList<Board> = mutableListOf()

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    val comments: MutableList<Comment> = mutableListOf()

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    val forumItems: MutableList<ForumItem> = mutableListOf()

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    val bbItems: MutableList<BlackboardItem> = mutableListOf()

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    var userConfigs: MutableList<UserModuleConfig> = mutableListOf()

    constructor() : this("", "", "", "", null)
    constructor(
            id: Long,
            name: String,
            email: String,
            pw: String,
            role: String,
            githubId: String?
    ) : this(name, email, pw, role, githubId){
        this.id = id
    }
}
