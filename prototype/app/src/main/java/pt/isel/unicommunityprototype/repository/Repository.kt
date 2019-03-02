package pt.isel.unicommunityprototype.repository

import pt.isel.unicommunityprototype.model.Board
import pt.isel.unicommunityprototype.model.Module
import pt.isel.unicommunityprototype.model.Post

class Repository {

    val boards = mutableListOf<Board>()
    val posts = mutableListOf<Post>()

    //TODO: Later in API
    // POST /api/board
    // body {
    //    name: "Programação na Internet"
    //    .....
    // }

    fun createBoard(name: String,
                    description: String?,
                    modules: List<Module>) : Board {
        val b = Board(name, description, modules)
        boards.add(b)

        return b
    }

    fun createPost(title: String,
                   content: String?) : Post {
        val p = Post(title, content)
        posts.add(p)

        return p
    }
}