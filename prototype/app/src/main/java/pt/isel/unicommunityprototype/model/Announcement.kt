package pt.isel.unicommunityprototype.model

import java.util.*

data class Announcement(
    val id: Int,
    val title: String,
    val content: String?,// = "" //TODO: is it better to define an optional/nullable like this String? or default value?
    val blackboard: Anuncios,// TODO: is this needed
    val author: User,
    val date: Date
)