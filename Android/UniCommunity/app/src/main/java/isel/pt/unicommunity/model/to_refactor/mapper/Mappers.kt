package isel.pt.unicommunity.model.to_refactor.mapper

import isel.pt.unicommunity.model.to_refactor.test.TestDto
import isel.pt.unicommunity.model.to_refactor.test.TestMapper

interface IMapper<DTO , T>{

    fun dtoToModel(dto : DTO): T
}

class Mappers {


    fun <DTO> get(a: Class<DTO>) : IMapper<*, *>?{

        when (a){

            TestDto::class.java ->{
                return TestMapper()
            }


            /*NavDto::class.java -> {
                if (b::class.java == Nav::class.java)
                    Log.v("as", "asdasdas")
                else
                    throw RuntimeException()
            }

            UserDto::class.java -> {
                if (b::class.java == User::class.java)
                    Log.v("as", "asdasdas")
                else
                    throw RuntimeException()
            }*/

        }

        return null

    }

}
