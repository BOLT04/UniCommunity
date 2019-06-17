package isel.pt.unicommunity.testing.fragmentTesting.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.Response
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.model.to_refactor.dto.users.UserDto
import isel.pt.unicommunity.repository.network.GetRequest
import kotlinx.android.synthetic.main.__testing__a_fragment.*

class FragmentB : Fragment(){

    val TAG = "TESTING"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.__testing__a_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()


        textBtn.setOnClickListener {
            Log.v(TAG, "button clicked")
            //textView.text= "macarena"

            val getRequest =
                GetRequest(
                    UserDto::class.java,
                    "https://www.google.com",
                    Response.Listener {
                        //textView.text = it.name
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

        }


    }

}