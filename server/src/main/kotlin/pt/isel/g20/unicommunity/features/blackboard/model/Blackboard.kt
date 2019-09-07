package pt.isel.g20.unicommunity.features.blackboard.model

import com.fasterxml.jackson.annotation.JsonIgnore
import pt.isel.g20.unicommunity.features.blackboardItem.model.BlackboardItem
import pt.isel.g20.unicommunity.features.board.model.Board
import pt.isel.g20.unicommunity.features.usersBlackboards.model.UsersBlackboards
import javax.persistence.*

@Entity
class Blackboard(
        @Column(nullable = false) var name: String,
        @Column var notificationLevel: String,
        @Column var description: String? = null,
        @JsonIgnore @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="boardId") var board: Board
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], mappedBy = "blackboard")
    var items: MutableList<BlackboardItem> = mutableListOf()

    @JsonIgnore
    @OneToMany(mappedBy = "blackboard", cascade = [CascadeType.REMOVE])
    val usersSettings : MutableList<UsersBlackboards> = mutableListOf()

    fun getFcmTopicName() :String = "${this.board.id}-${this.id}-${this.name}"
}