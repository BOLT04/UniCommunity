package isel.pt.unicommunity.presentation.fragment.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import isel.pt.unicommunity.BackStackManagingActivity
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.presentation.adapter.BoardMenuAdapter
import isel.pt.unicommunity.presentation.adapter.SmallModuleClickListener
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.to_refactor.small.SmallModule
import isel.pt.unicommunity.presentation.fragment.modules.BlackBoardFragment
import isel.pt.unicommunity.presentation.fragment.modules.ForumFragment
import isel.pt.unicommunity.presentation.viewmodel.BoardViewModel
import kotlinx.android.synthetic.main.fragment_all_boards.*

class BoardMenuFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_boards, container, false)
    }

    override fun onStart() {
        super.onStart()

        val activity = (activity as AppCompatActivity)

        val app = activity.getUniCommunityApp()

        val viewModel = activity.getViewModel("boardID"){
            BoardViewModel()
        }


        recyclerView.layoutManager = LinearLayoutManager(activity)
        val onBoardClickListener = object : SmallModuleClickListener {
            override fun onClickListener(smallModule: SmallModule?) {

                if(smallModule!=null)
                    if(smallModule.type == "forum"){
                        (activity as BackStackManagingActivity).navigateTo(ForumFragment())
                    }
                    else{
                        (activity as BackStackManagingActivity).navigateTo(BlackBoardFragment())
                    }

            }
        }

        recyclerView.adapter = BoardMenuAdapter(viewModel, onBoardClickListener)

        viewModel.liveData.observe(
            this,
            Observer { recyclerView.adapter =  BoardMenuAdapter(viewModel, onBoardClickListener)}
        )
    }

}