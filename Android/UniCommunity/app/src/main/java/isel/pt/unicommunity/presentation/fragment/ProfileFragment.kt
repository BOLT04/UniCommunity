package isel.pt.unicommunity.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import isel.pt.unicommunity.presentation.activity.BackStackManagingActivity

import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.UserProfileLink
import isel.pt.unicommunity.presentation.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment(userProfile: UserProfileLink) : Fragment() {

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


    }


}
