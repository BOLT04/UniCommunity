package isel.pt.unicommunity.model.refactor_me_dad.small

import isel.pt.unicommunity.model.refactor_me_dad.built.User
import java.util.*

class SmallForumItem (
    val title: String,
    //val content: String, todo nao ter isto aqui?
    val solved: Boolean,
    val date: Date,
    val user: User //TODO tirar isto e isto seria o lightweight?
){
}