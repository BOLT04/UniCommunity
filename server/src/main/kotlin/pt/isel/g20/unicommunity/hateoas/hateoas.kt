package pt.isel.g20.unicommunity.hateoas

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude

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

/**
 * Abstract class to be used as a base class for HAL representations.
 */
abstract class HalObject(val _links: Map<String, Link>)


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
        //TODO:val data: Array
        val links: List<CollectionLink>? = null
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
data class ProblemJson(
        val type: String? = null,
        val title: String? = null,
        val detail: String? = null,
        val status: Int? = null,
        val instance: String? = null
)