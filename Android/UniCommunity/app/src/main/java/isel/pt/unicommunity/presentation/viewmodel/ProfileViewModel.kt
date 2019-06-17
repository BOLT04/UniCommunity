package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.to_refactor.built.User

class ProfileViewModel(app : UniCommunityApp) : ViewModel() {

    val user = MutableLiveData<User>()


}