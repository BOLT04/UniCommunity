package isel.pt.unicommunity.model.collectionjson

import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.Rels
import isel.pt.unicommunity.model.links.*

fun CollectionJson.toCommentCollection() : CommentCollection {

    with(this.collection){
        return CommentCollection(
            GetMultipleCommentsLink(href),
            items.map {
                with(DataExtractor(it.data, it.links)) {
                    CommentCollection.PartialComment(
                        GetSingleCommentLink(it.href),
                        getValue("id"),
                        getValue("content"),
                        getOptionalValue("authorName"),
                        getValue("createdAt"),
                        getLink(Rels.EDIT_COMMENT, ::EditCommentLink),
                        getLink(Rels.GET_MULTIPLE_COMMENTS, ::GetMultipleCommentsLink),
                        getLink(Rels.GET_SINGLE_FORUMITEM, ::GetSingleForumItemLink),
                        getLink(Rels.GET_SINGLE_BOARD, ::GetSingleBoardLink),
                        getLink(Rels.GET_SINGLE_USER, ::GetSingleUserLink)
                    )
                }
            }
        )
    }

}


class CommentCollection(
    val self: GetMultipleCommentsLink,
    val comments : List<PartialComment>
){
    class PartialComment(
        val self: GetSingleCommentLink,

        val id:String,
        val content : String,
        val authorName : String?,
        val createdAt : String,

        val editCommentLink: EditCommentLink?,
        //val deleteCommentLink: DeleteCommentLink?,
        val getMultipleCommentsLink: GetMultipleCommentsLink?,

        val getSingleForumItemLink: GetSingleForumItemLink?,
        val getSingleBoardLink: GetSingleBoardLink?,
        val getSingleUserLink: GetSingleUserLink?


    )
}