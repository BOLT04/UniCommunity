package pt.isel.unicommunityprototype.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import pt.isel.unicommunityprototype.R
import pt.isel.unicommunityprototype.kotlinx.getUniApplication
import pt.isel.unicommunityprototype.model.Student

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = getUniApplication()

        //TODO: this line below will be elsewhere...it cant be in activity
        app.repository.currentUser = Student(42934, "Sergiu", "42934@alunos.isel.pt", emptyList())
        app.repository.registerListener()

        createBoardBtn.setOnClickListener {
            startActivity(Intent(this, CreateBoardActivity::class.java))
        }
        gotoUserPanelBtn.setOnClickListener {
            startActivity(Intent(this, UserPanelActivity::class.java))
        }
    }
}
