package pt.isel.g20.unicommunity.features.comment.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import pt.isel.g20.unicommunity.features.forumItem.model.ForumItem
import pt.isel.g20.unicommunity.features.report.model.Report
import pt.isel.g20.unicommunity.features.user.model.User
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.persistence.*

@Entity
class Comment(
        @ManyToOne @JoinColumn(name = "forumItemId")var forumItem: ForumItem,
        @ManyToOne @JoinColumn var author: User,
        @Column(nullable = false) var content: String,
        @Column(nullable = false) var anonymousComment: Boolean

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss")
    @Column
    @CreationTimestamp
    var createdAt: Date? = null

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", cascade = [CascadeType.REMOVE])
    val reports: MutableList<Report> = mutableListOf()

    fun getDateFormatted() : String {
        val dateFormat: DateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")
        return dateFormat.format(createdAt)
    }
}