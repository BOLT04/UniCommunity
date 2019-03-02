package pt.isel.unicommunityprototype.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_post_details.*
import pt.isel.unicommunityprototype.R
import pt.isel.unicommunityprototype.kotlinx.getUniApplication

class PostDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        val app = getUniApplication()

        val postId = intent.getIntExtra("postId", 0)
        val boardTitle = intent.getStringExtra("boardTitle")

        val post = app.repository.getPostById(postId)

        // Set all information based on post instance
        forumNameTxtView.text = boardTitle
        authorTextView.text = post?.author?.name
        postTitleTxtView.text = post?.title
        postContentTxtView.setText(post?.content)
    }
}
