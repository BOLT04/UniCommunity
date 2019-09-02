package isel.pt.unicommunity.presentation.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.GetMultipleBoardsLink
import isel.pt.unicommunity.presentation.fragment.board.AllBoardsFragment
import isel.pt.unicommunity.presentation.viewmodel.BackStackManagingViewModel

class SimpleActivity : AppCompatActivity(), BackStackManagingActivity {

    lateinit var backstackVM : BackStackManagingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        backstackVM = getViewModel("SimpleActivity") {
            BackStackManagingViewModel(R.id.simple_act_frame_layout)
        }

        setContentView(R.layout.activity_simple)


        val getMultipleBoardsLink = GetMultipleBoardsLink(intent.getStringExtra("boardsHref"))

        navigateTo(AllBoardsFragment(getMultipleBoardsLink))

    }

    override fun onBackPressed() {
        if(!backstackVM.navigateBack(supportFragmentManager))
            super.onBackPressed()
    }

    override fun navigateTo(fragment: Fragment, inBackStack: Boolean, reseting: Boolean) {
        backstackVM.navigateTo(fragment, supportFragmentManager, inBackStack, reseting)
    }


}