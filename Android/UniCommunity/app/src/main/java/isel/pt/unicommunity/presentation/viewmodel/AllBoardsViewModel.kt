package isel.pt.unicommunity.presentation.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import isel.pt.unicommunity.model.to_refactor.small.SmallBoardItem

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
            SmallBoardItem("asc"),
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
