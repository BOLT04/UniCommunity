package pt.isel.unicommunityprototype.model

data class Post(
    val title: String,
    val content: String?// = "" //TODO: is it better to define an optional/nullable like this String? or default value?
    //, val flair: Flair
    //, val tags: List<Tag>
)