package isel.pt.unicommunity.model.webmapper

import isel.pt.unicommunity.model.webdto.clean.NavLink
import isel.pt.unicommunity.model.webdto.clean.Property
import java.lang.RuntimeException

/*fun parseRel(navLink: NavLink?): Ret?{
    return if(navLink==null)
        null
    else
        Ret(navLink.rel, navLink.href)
}*/

fun checkNotNull(vararg props : Property?){

    props.forEach { if(it==null) throw RuntimeException("isto nao vem ca, mas tambem nao deve explodir")}
    //todo

}