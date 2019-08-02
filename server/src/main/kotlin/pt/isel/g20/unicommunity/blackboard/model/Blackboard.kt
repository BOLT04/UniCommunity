package pt.isel.g20.unicommunity.blackboard.model

import com.fasterxml.jackson.annotation.JsonIgnore
import pt.isel.g20.unicommunity.blackboardItem.model.BlackboardItem
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.fcm.UserModuleConfig
import javax.persistence.*

@Entity
class Blackboard(
        @Column(nullable = false) var name: String,
        @JsonIgnore @OneToMany(fetch = FetchType.LAZY) var userConfigs: UserModuleConfig,
        @Column var notificationLevel: String, // Default notification level for all users.
        @Column var description: String? = null,
        @JsonIgnore @ManyToOne(fetch = FetchType.LAZY) var board: Board
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    var items: MutableList<BlackboardItem> = mutableListOf()
}