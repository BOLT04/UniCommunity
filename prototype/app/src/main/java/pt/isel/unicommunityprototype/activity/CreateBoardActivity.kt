package pt.isel.unicommunityprototype.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_board.*
import pt.isel.unicommunityprototype.R
import pt.isel.unicommunityprototype.kotlinx.getUniApplication
import pt.isel.unicommunityprototype.model.*

class CreateBoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_board)

        val app = getUniApplication()

        createBtn.setOnClickListener {
            val modules = mutableListOf<Module>()
            var forum : Forum? = null
            //TODO: This is wrong, we cant instantiate model classes on the activity...or can we??????????????
            if (forumCheckBox.isChecked)
                forum = Forum()
            if (sumariosCheckBox.isChecked)
                modules.add(Sumarios())
            if (recursosCheckBox.isChecked)
                modules.add(Recursos())
            if (anunciosCheckBox.isChecked)
                modules.add(Anuncios())
            if (bibliografiaCheckBox.isChecked)
                modules.add(Bibliografia())

            val boardId = app.repository.createBoard(
                nameText.text.toString(),
                descText.text.toString(),
                modules,
                forum
            )

            val intent = Intent(this, BoardDetailsWithTabsActivity::class.java)
            intent.putExtra("boardId", boardId)
            startActivity(intent)
        }
    }
}
