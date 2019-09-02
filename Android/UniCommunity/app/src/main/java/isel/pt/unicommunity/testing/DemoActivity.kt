package isel.pt.unicommunity.testing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Response
import isel.pt.unicommunity.presentation.activity.MainActivity
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.model.links.CreateForumItemLink
import isel.pt.unicommunity.model.outputdto.ForumItemOutputDto
import isel.pt.unicommunity.repository.network.BasicAuthNavLinkPostRequest
import isel.pt.unicommunity.testing.fragmentTesting.TestingActivity
import isel.pt.unicommunity.testing.markdowntesting.ExternalLibActivity
import isel.pt.unicommunity.testing.viewmodeltesting.ZActivity1
import isel.pt.unicommunity.testing.volleytesting.activity.VolleyActivity
import kotlinx.android.synthetic.main.__testing__demo.*


class DemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.__testing__demo)

        val app = getUniCommunityApp()

        button.setOnClickListener {

            val req = BasicAuthNavLinkPostRequest(
                CreateForumItemLink("/boards/1/forum/posts"),
                ForumItemOutputDto("name", "content", false),
                Response.Listener {
                    val a = it
                },
                Response.ErrorListener {
                    val b = it
                },
                "admin@gmail.com",
                "admin"
            )

            app.queue.add(req)

        }

        /*button8.setOnClickListener {

        }

        button9.setOnClickListener {
            this.startActivity(Intent(this, ExternalLibActivity::class.java))
        }

        button11.setOnClickListener {
            this.startActivity(Intent(this, MainActivity::class.java))
        }

        button10.setOnClickListener {
            this.startActivity(Intent(this, VolleyActivity::class.java))
        }*/

    }

}
