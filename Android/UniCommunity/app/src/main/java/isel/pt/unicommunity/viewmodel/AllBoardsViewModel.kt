package isel.pt.unicommunity.viewmodel

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import isel.pt.unicommunity.model.SmallBoardItem

class AllBoardsViewModel : BoardsViewModel(){

    val liveData : MutableLiveData<List<SmallBoardItem>> = MutableLiveData()

    init {
        val a = mutableListOf(
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc"),
            SmallBoardItem("asc" ),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa")
        )

        Handler().postDelayed({ liveData.value= a }, 5000)



    }
}
