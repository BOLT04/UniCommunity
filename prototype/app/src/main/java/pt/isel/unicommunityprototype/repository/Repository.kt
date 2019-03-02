package pt.isel.unicommunityprototype.repository

import pt.isel.unicommunityprototype.model.*

//TODO: THe interface of the Repository will be asynchronous later
class Repository {

    private val boards = hashMapOf<Int, Board>()
    //val posts = mutableListOf<Post>()
    private val posts = hashMapOf<Int, Post>()

    var currentUser: User? = null

    //TODO: Later in API
    // POST /api/board
    // body {
    //    name: "Programação na Internet"
    //    .....
    // }

    var boardId = 1 // TODO: later change this id incrementation -> back end
    fun createBoard(name: String,
                    description: String?,
                    modules: List<Module>,
                    forum: Forum?) : Int {
        val b = Board(boardId++, name, description, modules, forum)
        boards[b.id] = b

        return b.id
    }

    fun getBoardById(id: Int) = boards[id]


    var postId = 1 // TODO: later change this id incrementation -> back end
    fun createPost(
            boardId: Int,
            title: String,
            content: String?) : Int {                                   //TODO: how to take out the double bang on user???
        val p = Post(postId++, title, content, boards[boardId]?.forum, currentUser!!) // TODO: the problem with Forum() is that each new post as a new forum instance when it should be the same...all posts are created within the same forum
        posts[p.id] = p

        return p.id
    }

    fun getPostById(id: Int) = posts[id]
}