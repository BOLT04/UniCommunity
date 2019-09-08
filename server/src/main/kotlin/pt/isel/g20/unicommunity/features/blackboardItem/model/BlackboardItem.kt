package pt.isel.g20.unicommunity.features.blackboardItem.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import pt.isel.g20.unicommunity.features.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.features.user.model.User
import java.text.DateFormat
import java.text.SimpleDateFormat
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss")
    @Column
    @CreationTimestamp
    var createdAt: Date? = null

    fun getDateFormatted() : String {
        val dateFormat: DateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")

        return dateFormat.format(createdAt)
    }
}
