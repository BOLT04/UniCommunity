package pt.isel.unicommunityprototype.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_board_details.*
import kotlinx.android.synthetic.main.board_details_with_recyclerview.*
import pt.isel.unicommunityprototype.R
import pt.isel.unicommunityprototype.adapter.BoardAdapter
import pt.isel.unicommunityprototype.common.BOARD_VIEW_MODEL_KEY
import pt.isel.unicommunityprototype.kotlinx.getUniApplication
import pt.isel.unicommunityprototype.kotlinx.getViewModel
import pt.isel.unicommunityprototype.model.BoardElement
import pt.isel.unicommunityprototype.viewmodel.BoardViewModel

class BoardViewWithRecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board_details_with_recyclerview)

        //TODO: what if this board doesnt have a Forum module!!
        //I need an object Board, if it has a forum in modules array -> btn visible, if not invisible!
        /*gotoForumBtn.setOnClickListener {
            startActivity(Intent(this, ForumActivity::class.java))
        }*/
        val app = getUniApplication()
        val viewModel = getViewModel(BOARD_VIEW_MODEL_KEY) {
            BoardViewModel()
        }

        val boardId = intent.getIntExtra("boardId", 0)
        val board = app.repository.getBoardById(boardId)
       // boardNameTextView.text = board?.name ?: "no name"

        val intent = Intent(this, BoardDetailsWithTabsActivity::class.java)

        val listener = object : BoardAdapter.OnBoardElemClickListener {
            override fun onBoardElemClick(elem: BoardElement?) {
                // Update current board?
                //app.repository.team = team!!
                //app.chatBoard.associateTeam(team)
                //intent.putExtra("boardId", board?.id)
                //startActivity(intent)
            }
        }

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BoardAdapter(viewModel, listener)

        viewModel.elements.observe(this, Observer<List<BoardElement>> {
            recyclerView.adapter = BoardAdapter(viewModel, listener)
        })

        //viewModel.updateBoards()

    }
}
