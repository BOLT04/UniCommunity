package isel.pt.unicommunity.model.to_refactor.mapper.users

import com.android.volley.RequestQueue
import com.android.volley.Response
import isel.pt.unicommunity.model.to_refactor.built.Nav
import isel.pt.unicommunity.model.to_refactor.built.User
import isel.pt.unicommunity.model.to_refactor.dto.navigation.NavDto
import isel.pt.unicommunity.model.to_refactor.dto.navigation.NavMapper
import isel.pt.unicommunity.model.to_refactor.dto.users.UserDto
import isel.pt.unicommunity.model.to_refactor.linker.Retriever
import isel.pt.unicommunity.model.to_refactor.mapper.IMapper
/*
class UserMapper(val queue: RequestQueue) : IMapper<UserDto,User>{


   override fun dtoToModel(dto: UserDto) : User {

       /r nav: NavRetriever? = null
       if (dto.nav != null)
           nav = NavRetriever(dto.nav.url, queue, NavMapper() )

       return User(
           dto.name,
           dto.githubId,

           nav = nav
       )

   }




}*/

/*class NavRetriever(val url: String, val queue: RequestQueue, val navMapper: NavMapper) : Retriever<Nav> {

   //todo fun get(onSuccess: (Nav)->Unit , onError: Response.ErrorListener){
   override fun get(onSuccess: Response.Listener<Nav>, onError: Response.ErrorListener) {
        val requestOf = getRequestOf<NavDto>(
           url,
           Response.Listener { onSuccess.onResponse(navMapper.dtoToModel(it)) },
           onError,
           null
       )

       queue.add(requestOf)

   }
}*/


