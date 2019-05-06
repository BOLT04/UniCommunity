package isel.pt.unicommunity.testing.volleytesting.dto

import com.fasterxml.jackson.annotation.JsonProperty

class SimplerDto (
    val msg : String,
    val authors : Array<Me>
)

class Me(
    val who: String,
    @JsonProperty("/ahaha/ahah")val whena: String
)

class SimpleModel()