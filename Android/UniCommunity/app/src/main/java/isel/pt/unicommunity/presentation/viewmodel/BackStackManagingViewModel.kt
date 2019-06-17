package isel.pt.unicommunity.presentation.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import isel.pt.unicommunity.R
import isel.pt.unicommunity.presentation.fragment.modules.ForumFragment
import isel.pt.unicommunity.testing.fragmentTesting.fragment.HomeFragment

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

    fun getStarter(starter : String): Fragment?{

        return when(starter){

            "Home" -> HomeFragment()//todo preload these
            "Forum" -> ForumFragment()

            else -> null
        }



    }






}