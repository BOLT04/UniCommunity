package pt.isel.g20.unicommunity.usersBlackboards.model

import com.fasterxml.jackson.annotation.JsonIgnore
import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.user.model.User
import javax.persistence.*

@Entity(name = "users_blackboards")
class UsersBlackboards(
        @JsonIgnore @ManyToOne @JoinColumn(name = "userId")
        val user : User,

        @JsonIgnore @ManyToOne @JoinColumn(name = "blackboardId")
        val blackboard :Blackboard,

        var notificationLevel: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}