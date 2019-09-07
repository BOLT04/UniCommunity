package pt.isel.g20.unicommunity.features.blackboardItem.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import pt.isel.g20.unicommunity.features.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.features.user.model.User
import java.util.*
import javax.persistence.*

@Entity
class BlackboardItem(
        @Column var name: String,
        @Column var content: String,
        @ManyToOne @JoinColumn var author: User,
        @JsonIgnore @ManyToOne @JoinColumn(name="blackboardId") var blackboard: Blackboard
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss", locale = "en_GB")
    @Column
    @CreationTimestamp
    var createdAt: Date? = null
}
