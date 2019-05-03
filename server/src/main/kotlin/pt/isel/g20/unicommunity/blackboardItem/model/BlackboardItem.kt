package pt.isel.g20.unicommunity.blackboardItem.model

import org.hibernate.annotations.CreationTimestamp
import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import java.util.*
import javax.persistence.*

@Entity
class BlackboardItem(
        @Column var name: String,
        @Column var content: String,
        @Column(nullable = false) var author: String
) {

    @ManyToOne
    var blackboard: Blackboard? = null

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        private set

    @Column
    @CreationTimestamp
    var createdAt: Date? = null

    constructor() : this( "", "", "Luis Vaz")

    constructor(id: Long, name: String, content: String, author: String)
            : this(name, content, author) {
        this.id = id
    }

    fun setId(id: Int) {
        this.id = id.toLong()
    }
}
