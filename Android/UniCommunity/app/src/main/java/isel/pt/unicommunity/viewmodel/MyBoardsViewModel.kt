package isel.pt.unicommunity.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import isel.pt.unicommunity.model.SmallBoardItem

class MyBoardsViewModel : BoardsViewModel(){

    val liveData : MutableLiveData<List<SmallBoardItem>> = MutableLiveData()

    init {
        val a = mutableListOf(
            SmallBoardItem("MY_asc", "asdadsa"),
            SmallBoardItem("MY_asc", "asdadsa"),
            SmallBoardItem("MY_asc", "asdadsa"),
            SmallBoardItem("MY_asc", "asdadsa"),
            SmallBoardItem("MY_asc", "asdadsa"),
            SmallBoardItem("MY_asc", "asdadsa"),
            SmallBoardItem("MY_asc"),
            SmallBoardItem("MY_asc" ),
            SmallBoardItem("MY_asc", "asdadsa"),
            SmallBoardItem("MY_asc", "asdadsa"),
            SmallBoardItem("MY_asc", "asdadsa"),
            SmallBoardItem("MY_asc", "asdadsa"),
            SmallBoardItem("MY_asc", "asdadsa"),
            SmallBoardItem("MY_asc", "asdadsa"),
            SmallBoardItem("MY_asc", "asdadsa"),
            SmallBoardItem("MY_asc", "asdadsa")
        )

        Handler().postDelayed({ liveData.value= a }, 5000)



    }
}
