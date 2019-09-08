package pt.isel.g20.unicommunity.features.comment.model

import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.features.user.model.User

fun Comment.toItemRepr(user: User): Item {
    val forumItemId = this.forumItem.id
    val boardId = this.forumItem.forum.board.id
    val authorId = this.author.id
    val data = mutableListOf(
            Data(name = "id", value = this.id.toString()),
            Data(name = "content", value = this.content),
            Data(name = "createdAt", value = this.getDateFormatted())
    )
    val links = mutableListOf(
            CollectionLink(
                    rel = "self",
                    href = Uri.forSingleCommentText(boardId, forumItemId, this.id)
            ),
            CollectionLink(
                    rel = Rels.GET_SINGLE_FORUMITEM,
                    href = Uri.forSingleForumItemText(boardId, forumItemId)
            ),
            CollectionLink(
                    rel = Rels.GET_SINGLE_FORUM,
                    href = Uri.forSingleForumText(boardId)
            ),
            CollectionLink(
                    rel = Rels.GET_SINGLE_BOARD,
                    href = Uri.forSingleBoardText(boardId)
            ),
            CollectionLink(
                    rel = Rels.GET_MULTIPLE_COMMENTS,
                    href = Uri.forAllComments(boardId, forumItemId)
            ),
            CollectionLink(
                    rel = Rels.GET_MULTIPLE_FORUMITEMS,
                    href = Uri.forAllForumItems(forumItemId)
            ),
            CollectionLink(
                    rel = Rels.GET_MULTIPLE_BOARDS,
                    href = Uri.forAllBoards()
            ),
            CollectionLink(
                    rel = Rels.CREATE_REPORT,
                    href = Uri.forAllReports()
            )
    )

    if(authorId == user.id)
        links.addAll(sequenceOf(
                CollectionLink(
                        rel = Rels.EDIT_COMMENT,
                        href = Uri.forSingleCommentText(boardId, forumItemId, this.id)
                ),
                CollectionLink(
                        rel = Rels.DELETE_COMMENT,
                        href = Uri.forSingleCommentText(boardId, forumItemId, this.id)
                )
        ))

    if(!this.anonymousComment){
        links.add(
                CollectionLink(
                        rel = Rels.GET_SINGLE_USER,
                        href = Uri.forSingleUserText(authorId)
                )
        )
        data.add(Data(name = "authorName", value = this.author.name))
    }

    return Item(
            href = Uri.forSingleCommentText(boardId, forumItemId, this.id),
            data = data,
            links = links
    )
}