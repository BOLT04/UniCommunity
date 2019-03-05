package pt.isel.unicommunityprototype.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_post.*
import pt.isel.unicommunityprototype.R
import pt.isel.unicommunityprototype.kotlinx.getUniApplication

class CreateAnnouncementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_announcement)

        val app = getUniApplication()

        val boardId = intent.getIntExtra("boardId", 0)

        //todo: do we add more information on names like create_postBtn or is it implicit by the context we are in
        createBtn.setOnClickListener {
            val title = titleTxtView.text.toString()
            val content = contentTxtView.text.toString()

            app.repository.createAnnouncement(boardId, title, content)

            //val intent = Intent(this, PostDetailsActivity::class.java)
            //intent.putExtra("postId", postId)
            //intent.putExtra("boardTitle", boardTitle)
            //startActivity(intent)
        }
    }
}
