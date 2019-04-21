package pt.isel.g20.unicommunity.blackboardItem.model

import javax.persistence.*

@Entity
class BlackboardItem(
    boardId: Long,
    bbId: Long,
    name: String,
    content: String) {

    @Column
    var boardId: Long = boardId
        private set

    @Column
    var bbId: Long = bbId
        private set

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        private set

    @Column
    var name: String = name

    @Column
    var content: String = content

    constructor() : this(0, 0, 0, "", "")

    constructor(boardId: Long, bbId: Long, id: Long, name: String, content: String)
            : this(boardId, bbId, name, content) {
        this.boardId = boardId
        this.bbId = bbId
        this.id = id
        this.name = name
        this.content = content
    }

    fun setBoardId(boardId: Int) {
        this.boardId = boardId.toLong()
    }

    fun setBbId(bbId: Int) {
        this.bbId = bbId.toLong()
    }

    fun setId(id: Int) {
        this.id = id.toLong()
    }
}
