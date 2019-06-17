package isel.pt.unicommunity.model.to_refactor.small

import java.util.*

class SmallBlackBoardItem(
    val title: String,
    val desc: String? = null,
    val author : String = "anonymous", //TODO tirar isto e isto seria o lightweight?
    val date : Date = Date()
)