package isel.pt.unicommunity.model.refactor_me_dad.built

import java.util.*

class Comment(
    val parent: Comment?,
    val content: String,
    val user: User,
    val date: Date
) {
}