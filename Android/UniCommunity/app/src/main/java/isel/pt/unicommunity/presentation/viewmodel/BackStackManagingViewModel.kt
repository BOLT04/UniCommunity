package isel.pt.unicommunity.presentation.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel

class BackStackManagingViewModel(private val containerId: Int) : ViewModel(){

    private var fragmentBackStack : MutableList<Fragment> = mutableListOf()


    fun navigateTo(fragment: Fragment, supportFragmentManager : FragmentManager, goToStack: Boolean = true, reseting: Boolean = false){

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
        supportFragmentManager.beginTransaction().replace(
            containerId,
            fragment
        ).commit()
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








}