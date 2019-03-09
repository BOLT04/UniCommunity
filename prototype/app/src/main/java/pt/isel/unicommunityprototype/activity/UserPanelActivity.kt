package pt.isel.unicommunityprototype.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_user_panel.*
import pt.isel.unicommunityprototype.R
import pt.isel.unicommunityprototype.adapter.BoardsAdapter
import pt.isel.unicommunityprototype.common.USER_PANEL_VIEW_MODEL_KEY
import pt.isel.unicommunityprototype.kotlinx.getUniApplication
import pt.isel.unicommunityprototype.kotlinx.getViewModel
import pt.isel.unicommunityprototype.model.Board
import pt.isel.unicommunityprototype.viewmodel.UserPanelViewModel

class UserPanelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_panel)

        boardsView.setHasFixedSize(true)
        boardsView.layoutManager = LinearLayoutManager(this)

        val app = getUniApplication()
        val viewModel = getViewModel(USER_PANEL_VIEW_MODEL_KEY) {
            UserPanelViewModel(app, app.repository)
        }
        val intent = Intent(this, BoardViewWithRecyclerViewActivity::class.java)

        val listener = object : BoardsAdapter.OnBoardClickListener {
            override fun onBoardClick(board: Board?) {
                // Update current board?
                //app.repository.team = team!!
                //app.chatBoard.associateTeam(team)
                intent.putExtra("boardId", board?.id)
                startActivity(intent)
            }
        }

        boardsView.adapter = BoardsAdapter(viewModel, listener)

        viewModel.boards.observe(this, Observer<MutableCollection<Board>> {
            boardsView.adapter = BoardsAdapter(viewModel, listener)
        })

        viewModel.updateBoards()
    }
}
