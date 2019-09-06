package isel.pt.unicommunity.model.links

import isel.pt.unicommunity.model.BodyNavLink
import isel.pt.unicommunity.model.NavLink
import isel.pt.unicommunity.model.Rels

class SubscribeLink (href: String) : BodyNavLink<SubscribeOutputDto, SubscribeInputDto>(Rels.SUBSCRIBE, href, SubscribeOutputDto::class.java, SubscribeInputDto::class.java)

class UnsubscribeLink (href: String) : NavLink<SubscribeInputDto>(Rels.UNSUBSCRIBE, href, SubscribeInputDto::class.java)

class UnSubscribeInputDto {

}

class SubscribeInputDto(
    val topics : Array<String>
)
class SubscribeOutputDto