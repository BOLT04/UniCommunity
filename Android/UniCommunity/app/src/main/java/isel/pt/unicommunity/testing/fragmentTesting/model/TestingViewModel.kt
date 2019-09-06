package isel.pt.unicommunity.testing.fragmentTesting.model

import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import isel.pt.unicommunity.R

class TestingViewModel : ViewModel(){


    private var fragmentBackStack : MutableList<Fragment> = mutableListOf()



    fun navigateTo(fragment: Fragment, supportFragmentManager : FragmentManager, goToStack: Boolean, reseting: Boolean ){

        supportNavigate(supportFragmentManager, fragment)

        if(goToStack)
            if(reseting)
                fragmentBackStack = mutableListOf(fragment)
            else
                fragmentBackStack.add(fragment)

    }

    private fun supportNavigate(
        supportFragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        /*supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            fragment
        ).commit()*/
    }

    fun navigateBack(fragmentManager : FragmentManager) : Boolean{

        if(fragmentBackStack.size==0)
            return false

        fragmentBackStack.removeAt(fragmentBackStack.size-1)

        if(fragmentBackStack.size==0)
            return false

        val previous = fragmentBackStack.last()

        supportNavigate(fragmentManager, previous)

        return true


    }



    val isDone : MutableLiveData<Boolean> = MutableLiveData(false)


    init {
        Handler().postDelayed({ isDone.value= true }, 5000)
    }




}