package isel.pt.unicommunity.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import isel.pt.unicommunity.BackStackManagingActivity

import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.presentation.viewmodel.ProfileViewModel
import isel.pt.unicommunity.testing.fragmentTesting.fragment.HomeFragment
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onStart() {
        super.onStart()

        val viewModel = (activity as AppCompatActivity).getViewModel("profile"){
            ProfileViewModel((activity as AppCompatActivity).getUniCommunityApp())
        }


        /*viewModel.user.value?.getBoards?.get(

            Response.Listener {
                if(it.navigation != null)
                    if(activity is BackStackManagingActivity)
                        (activity as BackStackManagingActivity).navigateTo(MyBoardsFragment())
                    else{
                        //TODO necessario?! activity.supportFragmentManager.
                    }
                else{



                }
            },
            Response.ErrorListener { Toast.makeText(activity, "shits fuckd yo", Toast.LENGTH_SHORT).show() }
        )*/

        launcher.setOnClickListener{
            if(activity is BackStackManagingActivity)
                (activity as BackStackManagingActivity).navigateTo(HomeFragment())
            else{

                //TODO necessario?! activity.supportFragmentManager.
            }
        }





    }


}
