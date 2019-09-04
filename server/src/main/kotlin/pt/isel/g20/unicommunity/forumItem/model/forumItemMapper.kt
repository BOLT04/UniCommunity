package pt.isel.g20.unicommunity.forumItem.model

import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.CollectionLink
import pt.isel.g20.unicommunity.hateoas.Data
import pt.isel.g20.unicommunity.hateoas.Item
import pt.isel.g20.unicommunity.user.model.User

fun ForumItem.toItemRepr(user: User): Item {
        val boardId = this.forum.board.id
        val forumId = this.forum.id
        val authorId = this.author.id
        val links = mutableListOf(
                CollectionLink(
                        rel = "self",
                        href = Uri.forSingleForumItemText(forumId, this.id)
                ),
                CollectionLink(
                        rel = Rels.GET_SINGLE_FORUM,
                        href = Uri.forSingleForumText(forumId)
                ),
                CollectionLink(
                        rel = Rels.GET_SINGLE_BOARD,
                        href = Uri.forSingleBoardText(boardId)
                ),
                CollectionLink(
                        rel = Rels.GET_MULTIPLE_COMMENTS,
                        href = Uri.forAllComments(forumId, this.id)
                ),
                CollectionLink(
                        rel = Rels.GET_MULTIPLE_FORUMITEMS,
                        href = Uri.forAllForumItems(forumId)
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
                                rel = Rels.EDIT_FORUMITEM,
                                href = Uri.forSingleForumItemText(forumId, this.id)
                        ),
                        CollectionLink(
                                rel = Rels.DELETE_FORUMITEM,
                                href = Uri.forSingleForumItemText(forumId, this.id)
                        )
                ))

        if(!this.anonymousPost)
                links.add(
                        CollectionLink(
                                rel = Rels.GET_SINGLE_USER,
                                href = Uri.forSingleUserText(authorId)
                        )
                )
        
        return Item(
                href = Uri.forSingleForumItemText(forumId, this.id),
                data = listOf(
                        Data(name = "name", value = this.name),
                        Data(name = "id", value = this.id.toString()),
                        Data(name = "content", value = this.content),
                        Data(name = "authorName", value = if (this.anonymousPost) null else this.author.name),
                        Data(name = "createdAt", value = this.createdAt.toString())
                ),
                links = links
        )
}