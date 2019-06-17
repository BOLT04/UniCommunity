package isel.pt.unicommunity.presentation.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import isel.pt.unicommunity.model.to_refactor.small.SmallModule

class BoardViewModel : ViewModel(){

    val liveData : MutableLiveData<List<SmallModule>> = MutableLiveData()

    init {
        val a = mutableListOf(
            SmallModule("anuncios"),
            SmallModule("trabalhos"),
            SmallModule("notas"),
            SmallModule("kebabs"),
            SmallModule("rak"),
            SmallModule("bananas"),
            SmallModule("pen"),
            SmallModule("pineapple"),
            SmallModule("uh"),
            SmallModule("pineapple-pen"),
            SmallModule("rak as waifu forum", "forum")
        )

        Handler().postDelayed({ liveData.value= a }, 5000)



    }


}