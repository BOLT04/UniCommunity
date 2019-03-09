package pt.isel.unicommunityprototype.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pt.isel.unicommunityprototype.model.BlackBoardItem
import pt.isel.unicommunityprototype.model.BoardElement
import pt.isel.unicommunityprototype.model.ForumItem
import pt.isel.unicommunityprototype.model.Header

class BoardViewModel : ViewModel() {
    val elements: MutableLiveData<MutableList<BoardElement>> = MutableLiveData()
     init {
         elements.value = mutableListOf()
         elements.value!!.add(Header("anuncios"))
         elements.value!!.add(BlackBoardItem("item1"))
         elements.value!!.add(BlackBoardItem("item2"))
         elements.value!!.add(BlackBoardItem("item3"))
         elements.value!!.add(BlackBoardItem("item4"))
         elements.value!!.add(BlackBoardItem("item5"))
         elements.value!!.add(BlackBoardItem("item6"))
         elements.value!!.add(BlackBoardItem("item7"))
         elements.value!!.add(BlackBoardItem("item8"))
         elements.value!!.add(BlackBoardItem("item9"))
         elements.value!!.add(BlackBoardItem("item10"))
         elements.value!!.add(Header("bnanads"))
         elements.value!!.add(BlackBoardItem("item-1"))
         elements.value!!.add(BlackBoardItem("item-2"))
         elements.value!!.add(Header("forum"))
         elements.value!!.add(ForumItem("item", false))
         elements.value!!.add(ForumItem("item", false))
         elements.value!!.add(ForumItem("item", false))
         elements.value!!.add(ForumItem("item", false))
         elements.value!!.add(ForumItem("item", false))
         elements.value!!.add(ForumItem("item", false))
         elements.value!!.add(ForumItem("item", false))
         elements.value!!.add(ForumItem("item", false))
     }

}
