package isel.pt.unicommunity.presentation.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import isel.pt.unicommunity.model.refactor_me_dad.small.SmallBoardItem

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
            SmallBoardItem("MY_asc"),
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
