package isel.pt.unicommunity.model.webdto.old.actions

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.webdto.*

class CreateBoardModel(
    val name: String,
    val templateId: Long?,
    val description: String?,
    val blackboardNames: List<String>?,
    val hasForum: Boolean?
)

class LoginOutputDto (val email: String, val password:String)

class LoginInputDto(
    val email : String,
    val name: String,
    val _links: LoginSuccessLinkStructure
){
    class LoginSuccessLinkStructure(
        /*val self : LoginLink?,
        @JsonProperty(Rels.NAVIGATION) val nav : NavigationLink?,
        @JsonProperty(Rels.GET_MULTIPLE_BOARDS) val getMultipleBoardsLink: GetMultipleBoardsLink?*/
    )
}


