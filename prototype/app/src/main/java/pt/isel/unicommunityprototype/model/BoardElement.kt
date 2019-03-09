package pt.isel.unicommunityprototype.model

sealed class BoardElement

data class Header(val title: String) : BoardElement()
data class ForumItem(val desc : String, val solved :Boolean) : BoardElement()
data class BlackBoardItem(val desc : String) : BoardElement()
