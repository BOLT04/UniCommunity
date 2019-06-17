package isel.pt.unicommunity.model.to_refactor.built

import java.util.*

class Comment(
    val parent: Comment?,
    val content: String,
    val user: User,
    val date: Date
) {
}