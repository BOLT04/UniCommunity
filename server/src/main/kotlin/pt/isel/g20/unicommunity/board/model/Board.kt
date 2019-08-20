package pt.isel.g20.unicommunity.board.model

import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.forum.model.Forum
import pt.isel.g20.unicommunity.user.model.User
import javax.persistence.*

@Entity
class Board(
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn var creator: User,
        @Column(nullable = false) var name: String,
        @Column var description: String? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], mappedBy = "board")
    var forum: Forum? = null

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], mappedBy = "board")
    val blackBoards: MutableList<Blackboard> = mutableListOf()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinTable(
            name = "boards_users",
            joinColumns = [JoinColumn(name = "board_id", referencedColumnName = "id")],
            inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
    )
    var members: MutableList<User> = mutableListOf()
}
