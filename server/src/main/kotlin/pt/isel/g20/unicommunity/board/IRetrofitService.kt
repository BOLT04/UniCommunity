package pt.isel.g20.unicommunity.board

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET


@JsonInclude(JsonInclude.Include.NON_NULL)
data class Post constructor(
        @JsonProperty("id") val id:Int,
        @JsonProperty("userId") val userId:Int,
        @JsonProperty("title") val title:String,
        @JsonProperty("body") val body:String)

interface RetrofitService {
    @GET("/posts")
    suspend fun getPosts(): Response<List<Post>>
}

object RetrofitFactory {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com"

    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(RetrofitService::class.java)
    }
}