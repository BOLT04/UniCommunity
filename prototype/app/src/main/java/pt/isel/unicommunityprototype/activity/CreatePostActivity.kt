package pt.isel.unicommunityprototype.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_post.*
import pt.isel.unicommunityprototype.R
import pt.isel.unicommunityprototype.repository.Repository

class CreatePostActivity : AppCompatActivity() {

    val repo = Repository() // TODO: this will be changed to be a dependency given by DI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        //todo: do we add more information on names like create_postBtn or is it implicit by the context we are in
        createBtn.setOnClickListener {
            val title = titleTxtView.text.toString()
            val content = contentTxtView.text.toString()

            repo.createPost(title, content)

            //startActivity(Intent(this, PostDetailsActivity::class.java))

        }
    }
}
