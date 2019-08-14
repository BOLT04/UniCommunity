package isel.pt.unicommunity.testing.volleytesting.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android.volley.Response
import isel.pt.unicommunity.BASEURL
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.repository.network.BasicAuthenticationGetRequest
import isel.pt.unicommunity.repository.network.ErrorResponse
import kotlinx.android.synthetic.main.__testing__volleytesting.*


class VolleyActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.__testing__volleytesting)

        val queue = getUniCommunityApp().queue
        val vm = getViewModel("a"){
            VolleyVM(queue)
        }

        //val m = NavigationLink("yeets")
        val a = 1

        vm.value.observe(this, Observer {
            val a = it
        })



        /*boards.setOnClickListener {
            queue.add(BasicAuthenticationGetRequest(
                AllBoardsDto::class.java,
                "$BASEURL/boards",
                Response.Listener {
                    val a = it
                    //val mapped = AllBoardsMapper().toModel(it)
                    val b =1
                },
                Response.ErrorListener {Toast.makeText(this, ErrorResponse(it.networkResponse).getParsedError().detail, Toast.LENGTH_SHORT).show()},
                "admin@gmail.com",
                "admin"
            ))
        }*/

        /*board.setOnClickListener {
            queue.add(BasicAuthenticationGetRequest(
                BoardDto::class.java,
                "$baseurl/boards/1",
                Response.Listener { val a = it },
                Response.ErrorListener {Toast.makeText(this, ErrorResponse(it.networkResponse).getParsedError().detail, Toast.LENGTH_SHORT).show()},
                "admin@gmail.com",
                "admin"
            ))
        }*/

        /*blackboards.setOnClickListener {
            queue.add(BasicAuthenticationGetRequest(
                AllBlackBoardsDto::class.java,
                "$BASEURL/boards/1/blackboards",
                Response.Listener {
                    val a = it
                    //val mapped = AllBlackBoardsMapper().toModel(it)
                    val b =1
                },
                Response.ErrorListener {Toast.makeText(this, ErrorResponse(it.networkResponse).getParsedError().detail, Toast.LENGTH_SHORT).show()},
                "admin@gmail.com",
                "admin"
            ))
        }
*/
        /*blackboard.setOnClickListener {
            queue.add(BasicAuthenticationGetRequest(
                BlackBoardDto::class.java,
                "$baseurl/boards",
                Response.Listener { val a = it },
                Response.ErrorListener {Toast.makeText(this, ErrorResponse(it.networkResponse).getParsedError().detail, Toast.LENGTH_SHORT).show()},
                "admin@gmail.com",
                "admin"
            ))
        }*/

    }

}




/*
//vm.startRequest(Response.Listener{Toast.makeText(this, "we gottem boys", Toast.LENGTH_SHORT).show()})
vm.altRequest(
Response.ErrorListener {




    Toast.makeText(this, ErrorResponse(it.networkResponse).getParsedError().detail, Toast.LENGTH_SHORT).show()

}

        /*
            vm.loginRequest(
                Response.Listener { val a= it },
                Response.ErrorListener {Toast.makeText(this, ErrorResponse(it.networkResponse).getParsedError().detail, Toast.LENGTH_SHORT).show()}
            )
*/

            /*val value = vm.value.value
            if(value ==null)

            else {

                if(value.navigator!=null) {
                    val navVm = getViewModel("") {
                        NavVM(queue, value.navigator)
                    }

                    navVm.startRequest(Response.Listener {

                        if (it.msg == "macarena")
                            startActivity(Intent(this, MainActivity::class.java))
                        else{
                            var text = ""
                            it.authors.forEach { me -> text+= me.who+"|"+me.whena+"|||" }
                            textView2.text =text
                        }

                    })

                }*/