package pt.isel.g20.unicommunity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import pt.isel.g20.unicommunity.hateoas.HalResourceObject

class Testing{


    @JsonInclude(JsonInclude.Include.NON_NULL)
    open class HalObject(
            //var _links: MutableMap<String, Link>? = null,
            var _embedded: MutableMap<String, HalObjCont>? = null) : HalResourceObject()// TODO: how to make embedded an array OR object

    interface HalObjCont

    open class HalObj : HalObjCont

    open class ListHalObj(
            @JsonIgnore val list: List<HalObj>
    ) : AbstractList<HalObj>(), HalObjCont {
        override val size: Int = list.size
        override fun get(index: Int) = list[index]
    }

}

fun main(){

    val objectMapper = ObjectMapper()
    class SimpleClass(
            val id: Int,
            val name: String,
            embedded : MutableMap<String, Testing.HalObjCont>? = null
    ): Testing.HalObject(embedded)

    class partial(
            val abs : Int
    ): Testing.HalObj()

    class Mutliples(
            list: List<Testing.HalObj>
    ): Testing.ListHalObj(list)

    val value = SimpleClass(1, "yes",
            mutableMapOf(
                    "yes" to partial(1),
                    "list" to Mutliples(
                            listOf(partial(2), partial(3))
                    )
            ))




    objectMapper.writeValue(System.out, value)


}