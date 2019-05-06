package isel.pt.unicommunity.testing.volleytesting.activity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.RequestQueue
import com.android.volley.Response
import isel.pt.unicommunity.repository.network.getRequestOf
import isel.pt.unicommunity.testing.volleytesting.dto.Dto
import isel.pt.unicommunity.testing.volleytesting.dto.Model
import isel.pt.unicommunity.testing.volleytesting.dto.ParsedComposite
import isel.pt.unicommunity.testing.volleytesting.dto.SimpleNavigator

class VolleyVM(val queue: RequestQueue) :ViewModel(){

    val value= MutableLiveData<Model>()


    fun startRequest(){

        val requestOf = getRequestOf<Dto>(
            "http://demo7373603.mockable.io/asd",
            Response.Listener { parse(it) },
            Response.ErrorListener { },
            null
        )

        queue.add(requestOf)


    }

    private fun parse(it: Dto) {

        val c = it.composite

        val com = ParsedComposite(c.composite_name+c.composite_value, c.composite_value+c.composite_name)

        var simpleN: SimpleNavigator? = null

        if(it.navigator!=null)
             simpleN = SimpleNavigator(it.navigator)


        Model(it.name,com, simpleN)

        value.value = Model(it.name,com, simpleN)



    }

}