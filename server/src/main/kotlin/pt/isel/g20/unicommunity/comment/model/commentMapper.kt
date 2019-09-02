package pt.isel.g20.unicommunity.comment.model

import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.CollectionLink
import pt.isel.g20.unicommunity.hateoas.Data
import pt.isel.g20.unicommunity.hateoas.Item
import pt.isel.g20.unicommunity.user.model.User

fun Comment.toItemRepr(user: User): Item {
    val links = mutableListOf(
            CollectionLink(
                    rel = "self",
                    href = Uri.forSingleCommentText(this.forumItem.forum.id, this.forumItem.id, this.id)
            ),
            CollectionLink(
                rel = Rels.GET_SINGLE_FORUMITEM,
                href = Uri.forSingleForumItemText(this.forumItem.forum.id, this.forumItem.id)
            ),
            CollectionLink(
                    rel = Rels.GET_SINGLE_FORUM,
                    href = Uri.forSingleForumText(this.forumItem.forum.id)
            ),
            CollectionLink(
                    rel = Rels.GET_SINGLE_BOARD,
                    href = Uri.forSingleBoardText(this.forumItem.forum.board.id)
            ),
            CollectionLink(
                    rel = Rels.GET_MULTIPLE_COMMENTS,
                    href = Uri.forAllComments(this.forumItem.forum.id, this.forumItem.id)
            ),
            CollectionLink(
                    rel = Rels.GET_MULTIPLE_FORUMITEMS,
                    href = Uri.forAllForumItems(this.forumItem.id)
            ),
            CollectionLink(
                    rel = Rels.GET_MULTIPLE_BOARDS,
                    href = Uri.forAllBoards()
            )
    )

    if(this.author.id == user.id)
        links.addAll(sequenceOf(
                CollectionLink(
                        rel = Rels.EDIT_COMMENT,
                        href = Uri.forSingleCommentText(this.forumItem.forum.id, this.forumItem.id, this.id)
                ),
                CollectionLink(
                        rel = Rels.DELETE_COMMENT,
                        href = Uri.forSingleCommentText(this.forumItem.forum.id, this.forumItem.id, this.id)
                )
        ))

    return Item(
            href = Uri.forSingleCommentText(this.forumItem.forum.id, this.forumItem.id, this.id),
            data = listOf(
                    Data(name = "id", value = this.id.toString()),
                    Data(name = "content", value = this.content),
                    Data(name = "authorName", value = if (this.anonymousComment) null else this.author.name),
                    Data(name = "createdAt", value = this.createdAt.toString())
            ),
            links = links
    )
}