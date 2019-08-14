package isel.pt.unicommunity.testing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import isel.pt.unicommunity.presentation.activity.MainActivity
import isel.pt.unicommunity.R
import isel.pt.unicommunity.testing.fragmentTesting.TestingActivity
import isel.pt.unicommunity.testing.markdowntesting.ExternalLibActivity
import isel.pt.unicommunity.testing.viewmodeltesting.ZActivity1
import isel.pt.unicommunity.testing.volleytesting.activity.VolleyActivity
import kotlinx.android.synthetic.main.__testing__demo.*


class DemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.__testing__demo)

        button.setOnClickListener {
            this.startActivity(Intent(this, TestingActivity::class.java))
        }

        button8.setOnClickListener {
            this.startActivity(Intent(this, ZActivity1::class.java))
        }

        button9.setOnClickListener {
            this.startActivity(Intent(this, ExternalLibActivity::class.java))
        }

        button11.setOnClickListener {
            this.startActivity(Intent(this, MainActivity::class.java))
        }

        button10.setOnClickListener {
            this.startActivity(Intent(this, VolleyActivity::class.java))
        }

    }

}
