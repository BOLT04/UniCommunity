package pt.isel.g20.unicommunity.features.usersBoards

import com.fasterxml.jackson.annotation.JsonIgnore
import pt.isel.g20.unicommunity.features.board.model.Board
import pt.isel.g20.unicommunity.features.user.model.User
import javax.persistence.*

@Entity(name = "users_boards")
class UsersBoards(
        @JsonIgnore @ManyToOne @JoinColumn(name = "userId")
        val user : User,

        @JsonIgnore @ManyToOne @JoinColumn(name = "boardId")
        val board : Board
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}