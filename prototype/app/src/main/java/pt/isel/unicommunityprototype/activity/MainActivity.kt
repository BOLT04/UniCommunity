package pt.isel.unicommunityprototype.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import pt.isel.unicommunityprototype.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createBoardBtn.setOnClickListener {
            startActivity(Intent(this, CreateBoardActivity::class.java))
        }
    }
}
