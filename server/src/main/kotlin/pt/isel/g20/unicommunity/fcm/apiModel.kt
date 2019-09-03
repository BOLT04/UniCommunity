package pt.isel.g20.unicommunity.fcm

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A successful response to the endpoint 'Create relationship maps for app instances' of the
 * Instance ID API, is an empty object.
 * On the link below check the section 'Create relationship maps for app instances'
 * @see [Docs](https://developers.google.com/instance-id/reference/server)
 */
class Empty @JsonCreator constructor()

class SuccessMessage @JsonCreator constructor(
        @JsonProperty("message_id") val messageId: Long
)

@JsonInclude(JsonInclude.Include.NON_NULL)
class FcmMessage @JsonCreator constructor(
        @JsonProperty("to")
        val to: String? = null,

        @JsonProperty("registration_ids")
        val registrationIds: MutableList<String>? = null,

        @JsonProperty("condition")
        val condition: String? = null,

        @JsonProperty("collapse_key")
        val collapseKey: String? = null,

        @JsonProperty("priority")
        val priority: String? = null,

        @JsonProperty("content_available")
        val contentAvailable: Boolean? = null,

        @JsonProperty("data")
        val data: MutableMap<String, String>? = null,

        @JsonProperty("notification")
        val notification: MutableMap<String, String>? = null
)