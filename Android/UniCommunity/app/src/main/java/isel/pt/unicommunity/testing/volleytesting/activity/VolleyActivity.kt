package isel.pt.unicommunity.testing.volleytesting.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import isel.pt.unicommunity.MainActivity
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.repository.network.ErrorResponse
import kotlinx.android.synthetic.main.__testing__volleytesting.*
import java.nio.charset.Charset


class VolleyActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.__testing__volleytesting)

        val queue = getUniCommunityApp().queue
        val vm = getViewModel("a"){
            VolleyVM(queue)
        }


        vm.value.observe(this, Observer {
            val a = it
        })



        button2.setOnClickListener {

            //vm.startRequest(Response.Listener{Toast.makeText(this, "we gottem boys", Toast.LENGTH_SHORT).show()})
            vm.altRequest(
                Response.ErrorListener {




                    Toast.makeText(this, ErrorResponse(it.networkResponse).getParsedError().detail, Toast.LENGTH_SHORT).show()

                }
            )

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
            }

        }

    }





