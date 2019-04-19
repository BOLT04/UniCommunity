package isel.pt.unicommunity.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import isel.pt.unicommunity.model.SmallBlackBoardItem

class BlackBoardViewModel : ViewModel(){

    val liveData : MutableLiveData<List<SmallBlackBoardItem>> = MutableLiveData()

    init {
        val a = mutableListOf(
            SmallBlackBoardItem( "we in this"),
            SmallBlackBoardItem( "amanha nao vou"),
            SmallBlackBoardItem( "amanha vou duas vezes"),
            SmallBlackBoardItem( "amanha eu vou e voces nao vao"),
            SmallBlackBoardItem( "rak is best"),
            SmallBlackBoardItem( "rak is waifu", "dont judge me please"),
            SmallBlackBoardItem( "♥_♥"),
            SmallBlackBoardItem( "rak as waifu forum", "forum")
        )

        Handler().postDelayed({ liveData.value= a }, 5000)



    }
}