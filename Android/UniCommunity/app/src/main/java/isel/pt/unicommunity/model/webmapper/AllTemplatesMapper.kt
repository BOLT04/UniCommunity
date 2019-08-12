package isel.pt.unicommunity.model.webmapper

import isel.pt.unicommunity.model.webdto.clean.AllTemplatesDto
import java.lang.RuntimeException

class Template(
    val name:String,
    val id: Int,
    val hasForum: Boolean,
    val blackBoardNames : List<String>
)

class AllTemplates(

    val templates: List<Template>,

    val self: Ret?,
    val navigation: Ret?,
    val home: Ret?

)

class AllTemplatesMapper : IMapper<AllTemplatesDto, AllTemplates> {

    override fun dtoToModel(dto: AllTemplatesDto): AllTemplates {
        val templates = dto.collection.items.map { item ->
            val data = item.data

            val name = data.firstOrNull { it.name == "APP_NAME" }
            val id = data.firstOrNull { it.name == "id" }
            val hasForum = data.firstOrNull { it.name == "hasForum" }
            val blackBoardNames = data.firstOrNull { it.name == "blackboardNames" }

            if (name == null || id==null || hasForum==null || blackBoardNames==null)
                throw RuntimeException("isto nao vem ca, mas tambem nao deve explodir")
            else{

                val intId :Int
                try {
                    intId = id.value.toInt()
                }catch (e : NumberFormatException ){throw RuntimeException("isto nao vem ca, mas tambem nao deve explodir")}

                Template(name.value, intId , hasForum.value.toBoolean(), blackBoardNames.value.split(","))

            }
        }

        val links = dto.collection.links

        val retMapper = RetMapper(links)

        val self = retMapper.get("self")
        val navigation =  retMapper.get("/rels/nav")
        val home =  retMapper.get("/rels/home")


        return AllTemplates(templates, self, navigation, home )


    }
}

