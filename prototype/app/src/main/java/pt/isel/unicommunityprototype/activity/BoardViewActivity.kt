package pt.isel.unicommunityprototype.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_board_details.*
import pt.isel.unicommunityprototype.R
import pt.isel.unicommunityprototype.kotlinx.getUniApplication

class BoardViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_details)

        //TODO: what if this board doesnt have a Forum module!!
        //I need an object Board, if it has a forum in modules array -> btn visible, if not invisible!
        /*gotoForumBtn.setOnClickListener {
            startActivity(Intent(this, ForumActivity::class.java))
        }*/
        val app = getUniApplication()

        val boardId = intent.getIntExtra("boardId", 0)
        val board = app.repository.getBoardById(boardId)
        boardNameTextView.text = board?.name

        createPostBtn.setOnClickListener {
            val boardTitle = boardNameTextView.text.toString()

            val intent = Intent(this, CreatePostActivity::class.java)
            intent.putExtra("boardTitle", boardTitle)
            intent.putExtra("boardId", boardId)
            startActivity(intent)
        }

        createAnnouncementBtn.setOnClickListener {
            val intent = Intent(this, CreateAnnouncementActivity::class.java)
            intent.putExtra("boardId", boardId)
            startActivity(intent)
        }
    }
}
