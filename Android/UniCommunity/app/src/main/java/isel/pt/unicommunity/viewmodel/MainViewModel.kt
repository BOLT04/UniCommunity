package isel.pt.unicommunity.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import isel.pt.unicommunity.R

class MainViewModel : ViewModel(){

    private var fragmentBackStack : MutableList<Fragment> = mutableListOf()


    fun navigateTo(fragment: Fragment, supportFragmentManager : FragmentManager, reseting: Boolean = false){

        supportNavigate(supportFragmentManager, fragment)

        if(reseting)
            fragmentBackStack = mutableListOf(fragment)
        else
            fragmentBackStack.add(fragment)

    }

    private fun supportNavigate(
        supportFragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            fragment
        ).commit()
    }

    fun navigateBack(fragmentManager : FragmentManager) : Boolean{

        fragmentBackStack.removeAt(fragmentBackStack.size-1)

        if(fragmentBackStack.size==0)
            return false

        val previous = fragmentBackStack.last()

        supportNavigate(fragmentManager, previous)

        return true


    }






}