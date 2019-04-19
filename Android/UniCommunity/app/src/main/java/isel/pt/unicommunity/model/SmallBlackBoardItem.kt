package isel.pt.unicommunity.model

import java.time.LocalDateTime
import java.util.*

class SmallBlackBoardItem(
    val title: String,
    val desc: String? = null,
    val author : String = "anonymous", //TODO tirar isto
    val date : Date = Date()
)