package pt.isel.unicommunityprototype.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_board_details.*
import pt.isel.unicommunityprototype.R

class BoardDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_details)

        //TODO: what if this board doesnt have a Forum module!!
        //I need an object Board, if it has a forum in modules array -> btn visible, if not invisible!
        /*gotoForumBtn.setOnClickListener {
            startActivity(Intent(this, ForumActivity::class.java))
        }*/

        createPostBtn.setOnClickListener {
            startActivity(Intent(this, CreatePostActivity::class.java))
        }
    }
}
