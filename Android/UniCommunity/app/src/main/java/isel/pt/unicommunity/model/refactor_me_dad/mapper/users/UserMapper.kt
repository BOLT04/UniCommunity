package isel.pt.unicommunity.model.refactor_me_dad.mapper.users

import com.android.volley.RequestQueue
import com.android.volley.Response
import isel.pt.unicommunity.model.refactor_me_dad.built.Nav
import isel.pt.unicommunity.model.refactor_me_dad.built.User
import isel.pt.unicommunity.model.refactor_me_dad.dto.navigation.NavDto
import isel.pt.unicommunity.model.refactor_me_dad.dto.navigation.NavMapper
import isel.pt.unicommunity.model.refactor_me_dad.dto.users.UserDto
import isel.pt.unicommunity.model.refactor_me_dad.linker.Retriever
import isel.pt.unicommunity.model.refactor_me_dad.mapper.IMapper
import isel.pt.unicommunity.repository.network.getRequestOf

class UserMapper(val queue: RequestQueue) : IMapper<UserDto,User>{


    override fun dtoToModel(dto: UserDto) : User {

        var nav: NavRetriever? = null
        if (dto.nav != null)
            nav = NavRetriever(dto.nav.url, queue, NavMapper() )

        return User(
            dto.name,
            dto.githubId,

            nav = nav
        )

    }




}

class NavRetriever(val url: String, val queue: RequestQueue, val navMapper: NavMapper) : Retriever<Nav> {

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
}


