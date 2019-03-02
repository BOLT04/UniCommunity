package pt.isel.unicommunityprototype.model

data class Post(
    val id: Int,
    val title: String,
    val content: String?,// = "" //TODO: is it better to define an optional/nullable like this String? or default value?
    val forum: Forum?,// TODO: it needs to be Forum? bc its being enforced by "boards[boardId]?.forum"...........
    val author: User
    //, val flair: Flair
    //, val tags: List<Tag>
    //, val comments: List<Comment>
)