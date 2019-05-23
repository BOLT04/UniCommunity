package pt.isel.g20.unicommunity.forumItem.model

import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*

fun ForumItem.toItemRepr() = Item(
        href = Uri.forSingleForumItemText(this.forum.id, this.id),
        data = listOf(
                Data(name = "name", value = this.name),
                Data(name = "id", value = this.id.toString()),
                Data(name = "content", value = this.content),
                Data(name = "authorName", value = this.author.name),
                Data(name = "createdAt", value = this.createdAt.toString())
        ),
        links = listOf(
                CollectionLink(
                        rel = "self",
                        href = Uri.forSingleForumItemText(this.forum.id, this.id)
                ),
                CollectionLink(
                        rel = Rels.EDIT_BLACKBOARD,
                        href = Uri.forSingleForumItemText(this.forum.id, this.id)
                ),
                CollectionLink(
                        rel = Rels.DELETE_BLACKBOARD,
                        href = Uri.forSingleForumItemText(this.forum.id, this.id)
                ),
                CollectionLink(
                        rel = Rels.GET_MULTIPLE_FORUMITEMS,
                        href = Uri.forAllForumItems(this.forum.id)
                ),
                CollectionLink(
                        rel = Rels.GET_MULTIPLE_BOARDS,
                        href = Uri.forAllBoards()
                )
        )
)
