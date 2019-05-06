package isel.pt.unicommunity.testing.volleytesting.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android.volley.Response
import isel.pt.unicommunity.MainActivity
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import kotlinx.android.synthetic.main.volleytesting.*


class VolleyActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.volleytesting)

        val queue = getUniCommunityApp().queue
        val vm = getViewModel("a"){
            VolleyVM(queue)
        }

        vm.value.observe(this, Observer { textView2.text = it.name })

        button2.setOnClickListener {
            val value = vm.value.value
            if(value ==null)
                vm.startRequest()
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

                }
            }

        }

    }





}