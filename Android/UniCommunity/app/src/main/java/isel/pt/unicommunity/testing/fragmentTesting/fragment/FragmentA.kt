package isel.pt.unicommunity.testing.fragmentTesting.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import kotlinx.android.synthetic.main.__testing__a_fragment.*

class FragmentA : Fragment() {

    val TAG = "TESTING"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.__testing__a_fragment, container, false)
    }


    override fun onStart() {
        super.onStart()

        val act = activity as AppCompatActivity

        val app = act.getUniCommunityApp()


        class Test<T>(val a : Class<T>)


        //val test = Test(User::class.java)


        //nav_header_email.text = test.a.simpleName



        /*val viewModel = act.getViewModel("test"){
           // TestingModel(app.navSupplier)
        }
*/
       /* viewModel.user.observe(act, Observer {

            nav_header_email.text= it.name

            /*it.test?.get(Response.Listener { testModel ->
                textView.text = testModel.APP_NAME
            },
                Response.ErrorListener {  }
            )*/

            //(it.test as Navigator).navigate(act)
        })*/


        /*




        button.setOnClickListener {
            Log.v(TAG, "button clicked")
            //textView.text= "macarena"

            viewModel.user.value?.nav?.get(
                Response.Listener { Toast.makeText(act,"success", Toast.LENGTH_LONG).show() },
                Response.ErrorListener { Toast.makeText(act,"failure", Toast.LENGTH_LONG).show() }
            )



        }*/


    }


}

/*
*
* val getRequest =
                GetRequest(
                    TestingModel::class.java,
                    "http://demo7373603.mockable.io/asd",
                    Response.Listener {
                        textView.text = it.msg
                        Log.v(TAG, "gotcha")

                    },
                    Response.ErrorListener {
                        textView.text = it.localizedMessage
                        Log.v(TAG, ""+it.message)
                        Log.v(TAG, ""+it.localizedMessage)
                        Log.v(TAG, ""+it)
                    },
                    null
                ) {
                    Log.v(TAG, "anyone there?")
                }

            (activity as AppCompatActivity).getUniCommunityApp().queue.add(getRequest)

*
*
* */