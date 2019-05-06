package isel.pt.unicommunity.model.webmapper

import isel.pt.unicommunity.model.webdto.FeedDto
import java.lang.RuntimeException

class FeddMapper : IMapper<FeedDto, Feed> {

    override fun dtoToModel(dto: FeedDto): Feed {

        val items = dto.collection.items.map { item ->

            val title = item.data.firstOrNull { it.name == "title" }
            val smallDesc = item.data.firstOrNull { it.name == "smallDesc" }
            val author = item.data.firstOrNull { it.name == "author" }
            val board = item.data.firstOrNull { it.name == "board" }
            val blackBoard = item.data.firstOrNull { it.name == "blackboard" }
            val createdAt = item.data.firstOrNull { it.name == "createdAt" }


            checkNotNull(title, author, board, createdAt)

            FeedItem(
                title!!.value,
                smallDesc?.value,
                author!!.value,
                board!!.value,
                blackBoard?.value,
                createdAt!!.value

            )

        }

        val retMapper = RetMapper(dto.collection.links)

        retMapper.get("rell")//todo


        return Feed(items)







    }

}

class Feed (
    val feedItems: List<FeedItem>
)

class FeedItem(
    val title :String,
    val smallDesc :String?,
    val authorName : String,
    val boardName:String,
    val blackBoardName:String?,
    val createdAt :String
)
