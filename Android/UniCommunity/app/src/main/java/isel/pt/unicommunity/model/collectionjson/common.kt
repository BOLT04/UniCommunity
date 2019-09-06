package isel.pt.unicommunity.model.collectionjson

import isel.pt.unicommunity.model.Link
import isel.pt.unicommunity.model.Property

class DataExtractor(data : Array<Property>, links: Array<Link>){
    private val propMap = HashMap<String,String>()
    private val linkMap = HashMap<String,String>()
    init {
        data.forEach { propMap[it.name]= it.value }
        links.forEach { linkMap[it.rel]=it.href }
    }

    fun getValue(propName: String): String {

        return propMap[propName] ?: throw NotValidPropertyException(propName)

    }

    fun getOptionalValue(propName: String): String? {

        return propMap[propName]

    }

    fun <T>getLink(rel: String, constructor: (String) -> T ): T? {
        val href = linkMap[rel] ?: return null
        return constructor(href)
    }


}

class NotValidPropertyException(propName: String) : Throwable()