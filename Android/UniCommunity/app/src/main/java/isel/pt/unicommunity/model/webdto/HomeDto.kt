package isel.pt.unicommunity.model.webdto

import com.fasterxml.jackson.annotation.JsonProperty

class HomeDto(

    val _links : HomeLinkStruct,
    val _embedded : A
)

class HomeLinkStruct (

    val self: SelfLink? = null,
    @JsonProperty("/rels/nav") val nav: NavigationLink? = null,
    @JsonProperty("/rels/feed")val feed: FeedLink? = null
)



class A(
    @JsonProperty("/rels/feed") feed : Array<SmallFeedItemDto>
)

class SmallFeedItemDto(
    val name: String,
    val shortDesc : String? =null,
    val author: String,
    val _links: SmallFeedItemLinkStruct
)

class SmallFeedItemLinkStruct(
    val self: SelfLink? = null,
    @JsonProperty("/rels/userProfile") val userProfile: UserProfileLink? = null
)
