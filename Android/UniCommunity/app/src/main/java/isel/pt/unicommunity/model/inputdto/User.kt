package isel.pt.unicommunity.model.inputdto

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.Rels
import isel.pt.unicommunity.model.links.EditUserLink
import isel.pt.unicommunity.model.links.GetSingleUserLink


class UserInputDto(
    val id : String,
    val name: String,
    val email : String,
    val role : String,
    val _links : LinkStruct
) {
    class LinkStruct(
        val self : GetSingleUserLink?,
        @JsonProperty(Rels.EDIT_USER) val editUserLink: EditUserLink?
    )
}