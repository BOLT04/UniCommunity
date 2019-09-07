package pt.isel.g20.unicommunity.features.board.model

import com.fasterxml.jackson.annotation.JsonIgnore
import pt.isel.g20.unicommunity.features.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.features.forum.model.Forum
import pt.isel.g20.unicommunity.features.user.model.User
import pt.isel.g20.unicommunity.features.usersBoards.UsersBoards
import javax.persistence.*

@Entity
class Board(
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="creatorId") var creator: User,
        @Column(nullable = false) var name: String,
        @Column var description: String? = null,
        @Column(nullable = false) var active: Boolean = true
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
    val usersBoards : MutableList<UsersBoards> = mutableListOf()

    fun getMembers() = usersBoards.map { it.user }
}
