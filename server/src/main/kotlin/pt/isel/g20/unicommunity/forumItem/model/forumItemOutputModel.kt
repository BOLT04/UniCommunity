package pt.isel.g20.unicommunity.forumItem.model

import pt.isel.g20.unicommunity.hateoas.*

class SingleForumItemResponse(forumItem: ForumItem)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleForumItem(forumItem.forum!!.board!!.id, forumItem.id).toString()),
                Rels.GET_MULTIPLE_FORUMITEMS to Link(Uri.forAllForumItems(forumItem.forum!!.board!!.id).toString()),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoard(forumItem.forum!!.board!!.id).toString())
        )
){
    val name : String = forumItem.name
    val content : String = forumItem.content
    val author : String = forumItem.author
    val createdAt : String = forumItem.createdAt.toString()
}


class MultipleForumItemsResponse(
        boardId: Long,
        forumItems : Iterable<ForumItem>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllForumItems(boardId).toString(),
        links = listOf(
                CollectionLink("self","http://localhost:8080/boards/$boardId/forum/posts"),
                CollectionLink(Rels.NAVIGATION, "http://localhost:8080/navigation"), //TODO: Clean up hardcoded string and prefix with localhost etc...and this is maybe later configured (domain and port) in application.properties
                CollectionLink(Rels.CREATE_FORUMITEM, "http://localhost:8080"+Uri.forAllForumItems(boardId).toString())
        ),
        items = forumItems.map {
            Item(
                href = Uri.forSingleForumItem(it.forum!!.board!!.id, it.id).toString(),
                data = listOf(
                    Data(name= "title", value= it.name),
                    Data(name= "id", value= it.id.toString()),
                    Data(name= "smallDesc", value= it.content),
                    Data(name= "author", value= it.author),
                    Data(name= "createdAt", value= it.createdAt.toString())
                ),
                links = listOf(//TODO: clean up this code. The links should be the same as the ones from a single forum item response
                    CollectionLink(
                        rel= "self",//TODO: fix hardcoded prefix below
                        href= "http://localhost:8080"+ Uri.forSingleForumItem(boardId, it.id)
                    ),
                    CollectionLink(
                        rel= Rels.NAVIGATION,
                        href= "http://localhost:8080"+ Uri.NAVIGATION_ROUTE
                    )
                )
            )
        }
)