package pt.isel.g20.unicommunity.board.model

import com.fasterxml.jackson.annotation.JsonIgnore
import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.forum.model.Forum
import pt.isel.g20.unicommunity.user.model.User
import pt.isel.g20.unicommunity.usersBoards.UsersBoards
import javax.persistence.*

@Entity
class Board(
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="creatorId") var creator: User,
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

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = [CascadeType.REMOVE])
    val members : MutableList<UsersBoards> = mutableListOf()
}
