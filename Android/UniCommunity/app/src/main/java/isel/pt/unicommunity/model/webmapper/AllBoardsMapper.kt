package isel.pt.unicommunity.model.webmapper

import isel.pt.unicommunity.model.webdto.AllBoardsDto
import isel.pt.unicommunity.model.webdto.Link
import java.lang.RuntimeException

class Board(
    val name:String,
    val desc: String?,

    val self: Ret?=null,
    val nav: Ret?=null,
    val blackboards: Ret?=null,
    val forum: Ret?=null

)

class AllBoards(

    val boards : List<Board>,

    val self : Ret?,
    val nav : Ret?

)

class Ret(
    val rel: String,
    val href : String
)

class AllBoardsMapper: IMapper<AllBoardsDto,AllBoards> {

    override fun dtoToModel(dto: AllBoardsDto): AllBoards {

        val boards = dto.collection.items.map { item ->

            val name = item.data.firstOrNull { it.name == "name" }
            val desc = item.data.firstOrNull { it.name == "description" }

            checkNotNull(name)

            Board(name!!.value, desc?.value)
        }

        val links = dto.collection.links

        val retMapper = RetMapper(links)

        val self = retMapper.get("self")
        val navigation = retMapper.get("navigation")


        return AllBoards(boards, self, navigation)

    }
 }

class RetMapper(links :Array<Link>){

    private val map = HashMap<String, Link>()

    init {
        links.forEach { map[it.rel] = it }
    }

    fun get(name: String): Ret?{

        val link = map[name]

        return if(link == null)
            null
        else
            Ret(link.rel, link.href)
    }


}



