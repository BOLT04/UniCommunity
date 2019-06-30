package pt.isel.g20.unicommunity.hateoas

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri

/**
 * Class whose instances represent links, as described in <a href="https://tools.ietf.org/html/draft-kelly-json-hal-08">
 * JSON Hypertext Application Language</a>
 */
/**
 * Classes used in Presentation Layer
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Link @JsonCreator constructor(
        val href: String,
        val templated: Boolean? = null)

open class HalResourceObject

/**
 * Abstract class to be used as a base class for HAL representations.
 *
 * The fields of this class can't be "vals" because the child classes initialize them in the init block, since they
 * sometimes need to make validations to the model object before creating a link.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
open class HalObject(
    var _links: MutableMap<String, Link>? = null,
    var _embedded: MutableMap<String, List<HalResourceObject>>? = null) : HalResourceObject()// TODO: how to make embedded an array OR object
{
    constructor() : this(null, null)
}

/**
 * Abstract class to be used as a base class for HAL-FORMS representations.
 */
abstract class HalFormsObject(val _links: Map<String, Link>, val _templates: HalFormsTemplateObject)

class HalFormsTemplateObject(val default: HalFormsTemplate)

@JsonInclude(JsonInclude.Include.NON_NULL)
class HalFormsTemplate(
        val title: String? = null,
        val method: String,
        val contentType: String? = null,
        val properties: List<Property>? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
class Property(
        val name: String,
        val prompt: String? = null,
        val readOnly: Boolean? = null,
        val required: Boolean? = null,
        val value: String? = null,
        val regex: String? = null,
        val templated: Boolean? = null
)


/**
 * Abstract class to be used as a base class for JsonCollection+json representations.
 */
class CollectionObject(val collection: JsonCollection)

@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class JsonCollection(
        val version: String,
        val href: String,
        val links: List<CollectionLink>? = null,
        val items: List<Item>? = null,
        val queries: List<Query>? = null,
        val template: Template? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
//TODO: is @JsonCreator really needed???
data class CollectionLink @JsonCreator constructor(
        val rel: String,
        val href: String,
        val templated: Boolean? = null)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Item(
        val href: String,
        val data: List<Data>? = null,
        val links: List<CollectionLink>? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Data(
    val name: String,
    val value: String? = null,// TODO: change the type from string to Value. According to the specification it can be string or number or boolean: A VALUE data type MUST be a NUMBER, STRING, or one of the following literal names: false, null, or true.
    val prompt: String? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Query(
        val rel: String,
        val href: String,
        val prompt: String? = null
        //TODO:val data: Array
)

data class Template(
        val data: String//todo:Array<Data>
)


/**
 * Class used for error models, based on the <a href="https://tools.ietf.org/html/rfc7807">Problem Json spec</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
open class ProblemJson(
        val type: String? = null,
        val title: String? = null,
        val detail: String? = null,
        val status: Int? = null,
        val instance: String? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
class ExtendedProblemJson(
        type: String? = null,
        title: String? = null,
        detail: String? = null,
        status: Int? = null,
        instance: String? = null,
        val links: MutableMap<String, Link>?
) : ProblemJson(type, title, detail, status, instance)

private fun authorizationProblemJson(): ExtendedProblemJson =
        ExtendedProblemJson(
                title = "Authorization required",
                detail = "Access was denied because the required authorization was not granted",
                status = HttpStatus.UNAUTHORIZED.value(),
                links = mutableMapOf(
                        Rels.LOGIN to Link(Uri.LOGIN_ROUTE)
                )
        )