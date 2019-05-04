package pt.isel.g20.unicommunity.user.model

import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.comment.model.Comment
import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import javax.persistence.*
import java.util.HashSet
import java.awt.print.Book
import javax.persistence.ManyToMany



@Entity(name = "users")
open class User(
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

    @ManyToMany(mappedBy = "members")
    val boards : MutableList<Board> = mutableListOf()

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    val comments: MutableList<Comment> = mutableListOf()

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    val forumItems: MutableList<ForumItem> = mutableListOf()

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
