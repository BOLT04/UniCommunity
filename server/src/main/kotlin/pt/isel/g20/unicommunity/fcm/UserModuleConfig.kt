package pt.isel.g20.unicommunity.fcm

import com.fasterxml.jackson.annotation.JsonIgnore
import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.blackboardItem.model.BlackboardItem
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.user.model.User
import javax.persistence.*

@Entity
class UserModuleConfig(
        @Column var notificationLevel: String,
        @JsonIgnore @ManyToOne(fetch = FetchType.LAZY) var user: User,
        @JsonIgnore @ManyToOne(fetch = FetchType.LAZY) var blackboard: Blackboard
) {

    //TODO: should the id be (userId, bbId)?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}