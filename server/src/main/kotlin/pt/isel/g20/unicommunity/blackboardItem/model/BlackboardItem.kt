package pt.isel.g20.unicommunity.blackboardItem.model

import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import javax.persistence.*

@Entity
class BlackboardItem(
    name: String,
    content: String) {

    @ManyToOne
    var blackboard: Blackboard? = null

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        private set

    @Column
    var name: String = name

    @Column
    var content: String = content

    constructor() : this( 0, "", "")

    constructor(id: Long, name: String, content: String)
            : this(name, content) {
        this.id = id
    }

    fun setId(id: Int) {
        this.id = id.toLong()
    }
}
