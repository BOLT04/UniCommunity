package pt.isel.g20.unicommunity.usersBlackboards

import com.fasterxml.jackson.annotation.JsonIgnore
import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.user.model.User
import javax.persistence.*

@Entity(name = "users_blackboards")
class UsersBlackboards(
        @JsonIgnore @ManyToOne(cascade = [CascadeType.REMOVE]) @JoinColumn(name = "userId")
        val user : User,

        @JsonIgnore @ManyToOne(cascade = [CascadeType.REMOVE]) @JoinColumn(name = "blackboardId")
        val blackboard :Blackboard,

        val notificationLevel: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
