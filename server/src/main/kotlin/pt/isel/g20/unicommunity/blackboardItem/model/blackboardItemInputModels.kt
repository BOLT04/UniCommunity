package pt.isel.g20.unicommunity.blackboardItem.model

import com.fasterxml.jackson.annotation.JsonCreator

data class BlackboardItemDto @JsonCreator constructor(val name: String, val content: String)