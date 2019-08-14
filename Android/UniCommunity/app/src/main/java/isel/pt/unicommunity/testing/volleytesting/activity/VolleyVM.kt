package isel.pt.unicommunity.testing.volleytesting.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.RequestQueue
import com.android.volley.Response
import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.Link
import isel.pt.unicommunity.model.links.LoginOutputDto
import isel.pt.unicommunity.repository.network.BasicAuthenticationGetRequest
import isel.pt.unicommunity.repository.network.PostRequest


class VolleyVM(val queue: RequestQueue) : ViewModel(){

    val value= MutableLiveData<CollectionJson>()


    /*fun startRequest(succ : Response.Listener<Dto>){

        val requestOf = getRequestOf<Dto>(
            "http://35.246.2.65/users",
            succ,
            Response.ErrorListener { },
            null
        )

        queue.add(requestOf)


    }*/
    fun altRequest(err : Response.ErrorListener){


       val url = "http://35.246.2.65/boards"

        queue.add(BasicAuthenticationGetRequest(CollectionJson::class.java, url, Response.Listener { value.value=it }, err,"admin@gmail.com", "admin"))


    }

    fun loginRequest(succ: Response.Listener<LoginOutputDto>, err : Response.ErrorListener){
        val url = "http://35.246.2.65/signin"

        val postRequest = PostRequest(
            LoginOutputDto::class.java,
            url,
            LoginOutputDto("admin@gmail.com", "admin"),
            succ,
            err
        )




        queue.add(postRequest)
    }





    class BoardDto(
        val name : String,
        val description: String,
        val templateName: String,
        val hasForum : Boolean,

        //val links: BoardInputDtoLinkStruct,
        val embedded : Array<SmallBoardRep>
    )

    class SmallBoardRep(val name : String, val links: Array<Link>)

/*

        class Gotcha(private val url: String, private val success : Response.Listener<Any>)
            : JsonRequest<Any>(Method.GET, url, "", success, error) {

            val TAG = "GetRequest"

            override fun parseNetworkResponseContentGson(response: NetworkResponse): Response< T> {
                val dataStr = String(response.data)
                Log.v(TAG, "parsing network response $dataStr")

                val mapper = jacksonObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                val resDto = mapper.readValue(dataStr, type ) as T

                logger?.invoke()

                val parseCacheHeaders = HttpHeaderParser.parseCacheHeaders(response)
                return Response.success(resDto, parseCacheHeaders)
            }

            override fun getHeaders(): MutableMap<String, String> {
                return headers ?: super.getHeaders()
            }
        }
*/


/*
    private fun parse(it: Dto) {

        val c = it.composite

        val com = ParsedComposite(c.composite_name+c.composite_value, c.composite_value+c.composite_name)

        var simpleN: SimpleNavigator? = null

        if(it.navigator!=null)
             simpleN = SimpleNavigator(it.navigator)


        Model(it.APP_NAME,com, simpleN)

        value.value = Model(it.APP_NAME,com, simpleN)



    }
*/
}