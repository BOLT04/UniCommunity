package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class ErrorHandlingMLD<S,E>{

    private val success = MutableLiveData<S>()
    private val error = MutableLiveData<E>()

    fun observe(owner: LifecycleOwner, successListener: Observer<S>, errorListener: Observer<E>){
        success.observe(owner, successListener)
        error.observe(owner, errorListener)
    }

    fun success (value: S) { success.value = value}
    fun error (value: E) { error.value = value }

}