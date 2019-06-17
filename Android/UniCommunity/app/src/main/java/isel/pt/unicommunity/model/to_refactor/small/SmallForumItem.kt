package isel.pt.unicommunity.model.to_refactor.small

import isel.pt.unicommunity.model.to_refactor.built.User
import java.util.*

class SmallForumItem (
    val title: String,
    //val content: String, todo nao ter isto aqui?
    val solved: Boolean,
    val date: Date,
    val user: User //TODO tirar isto e isto seria o lightweight?
){
}