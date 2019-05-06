package isel.pt.unicommunity.model.refactor_me_dad.linker

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.widget.Toast
import com.android.volley.Response
import isel.pt.unicommunity.EmptyAFActivity
import isel.pt.unicommunity.MainActivity
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.refactor_me_dad.built.User
import isel.pt.unicommunity.model.refactor_me_dad.dto.HalStructure
import isel.pt.unicommunity.model.refactor_me_dad.mapper.IMapper
import isel.pt.unicommunity.model.refactor_me_dad.test.TestDto
import isel.pt.unicommunity.model.refactor_me_dad.test.TestFragment
import isel.pt.unicommunity.model.refactor_me_dad.test.TestModel
import isel.pt.unicommunity.repository.network.GetRequest

class ActualRetriever<T>(
    val clazz: Class<T>,
    val url: String,
    val headers : List<String>?,
    val body : Map<String, String>?,
    val app: UniCommunityApp
): Retriever<T>
{

    override fun get( onSuccess :Response.Listener<T>, onError : Response.ErrorListener ){
        val request = GetRequest(
            clazz,
            url,
            onSuccess,
            onError,
            parseHeaders(headers)
        )
        app.queue.add(
            request
        )

    }


    fun parseHeaders(headers: List<String>?): MutableMap<String, String>? {

        val map = mutableMapOf<String, String>()

        headers?.map(this::parseHeader)?.forEach{

            if(it!=null)
                map[it.first] = it.second
        }


        return  if (map.isEmpty())
                    null
                else
                    map
    }

    private fun parseHeader(headerName : String) : Pair<String, String>?{


        //todo supported headers?
        //todo static types application wide?
        return when(headerName){

            "security" -> headerName to "value"
            "content-type" -> headerName to "value"

            else -> null
        }


    }


}

open class MockRetriever<DTO, MOD>( ) : Retriever<MOD> {

    override fun get(onSuccess: Response.Listener<MOD>, onError: Response.ErrorListener) {

    }

}

class MockTestRetriever(
    val halStructure: HalStructure<TestDto>,  val mapper : IMapper<TestDto, TestModel>
) : Retriever<TestModel> , Navigator{


    override fun navigate(activity: Activity) {
        get(
            Response.Listener {

                if(it.name=="withNav"){
                    if(activity is MainActivity)
                        activity.navigateTo(TestFragment())
                    else{
                        val intent = Intent(activity, MainActivity::class.java)
                        intent.putExtra("initial" , "testing")

                        activity.startActivity(intent)
                    }
                }
                else
                    activity.startActivity(Intent(activity, EmptyAFActivity::class.java))

            },
            Response.ErrorListener { Toast.makeText(activity, "error fecthing", Toast.LENGTH_LONG).show() }
        )
    }


    override fun get(onSuccess: Response.Listener<TestModel>, onError: Response.ErrorListener) {

        Handler().postDelayed({

            val testDto = TestDto("namerinos")

            Handler().postDelayed({

                onSuccess.onResponse(mapper.dtoToModel(testDto))

            }, 2500)
        }, 2500)




    }

}


/*class MockUser : MockRetriever<User>(){

    override fun get(onSuccess: Response.Listener<User>, onError: Response.ErrorListener) {

        val u  = User(
            "randomName",
            "randomID"
        )

        Handler().postDelayed({ onSuccess.onResponse(u) }, 5000)
    }
}*/


interface Retriever<R>{
    fun get( onSuccess :Response.Listener<R>, onError : Response.ErrorListener )
}

interface Navigator{

    fun navigate ( activity: Activity)

}




class ActualNavSupplier(val app: UniCommunityApp){

    inline fun <reified M> getInstance(
        url: String,
        requiredHeaders: List<String>?,
        body: Map<String, String>?
    ): Retriever<M> =
        ActualRetriever(
            M::class.java,
            url,
            requiredHeaders,
            body,
            app
        )

    /*inline fun <reified P, M> getInstance(
        halStructure: HalStructure<P, M>
    ): Retriever<P>  =
        ActualRetriever(
            P::class.java,
            halStructure.url,
            halStructure.requiredHeaders,
            halStructure.body,
            app
        )*/


}

class MockNavSupllier(val app: UniCommunityApp){

    /*inline fun <reified M> getInstance(
        url: String,
        requiredHeaders: List<String>?,
        body: Map<String, String>?
    ): Retriever<M> =
        MockRetriever()*/

    inline fun <reified DTO, MOD> getInstance(
        halStructure: HalStructure<DTO>
    ): Retriever<MOD>? {


        when(halStructure.dto){

            TestDto::class.java ->{

                //MockTestRetriever(halStructure, )

                /*val mapper = Mappers().get(halStructure.dto)



                mapper.toString()*/
                //return MockTestRetriever( halStructure as HalStructure<TestDto>, mapper!!) as Retriever<MOD>

            }

           /* Nav::class.java ->{
                Mappers().get(halStructure.dto, halStructure.model)
            }

            User::class.java -> {
                Mappers().get(halStructure.dto, halStructure.model)
            }*/

        }



        val kClass = User::class.java





    return null


    }


}

/*class MockNavRetriever(halStructure: HalStructure<NavDto, Nav>) : Retriever<Nav>{

    override fun get(onSuccess: Response.Listener<Nav>, onError: Response.ErrorListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
*/
/*class MockUserRetriever : Retriever<UserDto> {

    override fun get(onSuccess: Response.Listener<UserDto>, onError: Response.ErrorListener) {

        val u  = UserDto(
            "randomName",
            "randomID"
        )


        Handler().postDelayed({ onSuccess.onResponse(u) }, 5000)


    }

}*/


class NavSupplier(app: UniCommunityApp, val mock : Boolean = false) {

    val actualNavSupplier = ActualNavSupplier(app)
    val mockNavSupllier = MockNavSupllier(app)


    /*inline fun <reified M> getInstance(
        url: String,
        requiredHeaders: List<String>?,
        body: Map<String, String>?
    ): Retriever<M> =
        if (mock)
            mockNavSupllier.getInstance(url, requiredHeaders, body)
        else
            actualNavSupplier.getInstance(url, requiredHeaders, body)
*/
    inline fun <reified P, reified M> getInstance(
        halStructure: HalStructure<P>
    ) : Retriever<M>?  =
        //if (mock)
            mockNavSupllier.getInstance<P,M>(halStructure)
       /* else
            actualNavSupplier.getInstance(halStructure)*/
}
