package pt.isel.unicommunityprototype.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import pt.isel.unicommunityprototype.UniCommunityApplication
import pt.isel.unicommunityprototype.model.Board
import pt.isel.unicommunityprototype.repository.Repository

class UserPanelViewModel(
    val app: UniCommunityApplication,
    private val repo : Repository
) : AndroidViewModel(app) {

    val boards: MutableLiveData<MutableCollection<Board>> = MutableLiveData()

    fun updateBoards() {
        //TODO: Async interface
        /*repo.getAllBoards({
            boards.value = it
        }, {
            defaultErrorHandler(app)
        })*/
        boards.value = repo.getAllBoards()
    }
}
