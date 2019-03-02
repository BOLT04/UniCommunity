package pt.isel.unicommunityprototype.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_board.*
import pt.isel.unicommunityprototype.R
import pt.isel.unicommunityprototype.model.*
import pt.isel.unicommunityprototype.repository.Repository

class CreateBoardActivity : AppCompatActivity() {
    val repo = Repository() // TODO: this will be changed to be a dependency given by DI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_board)

        createBtn.setOnClickListener {
            val modules = mutableListOf<Module>()
            if (forumCheckBox.isChecked)
                modules.add(Forum())
            if (sumariosCheckBox.isChecked)
                modules.add(Sumarios())
            if (recursosCheckBox.isChecked)
                modules.add(Recursos())
            if (anunciosCheckBox.isChecked)
                modules.add(Anuncios())
            if (bibliografiaCheckBox.isChecked)
                modules.add(Bibliografia())

            val board = repo.createBoard(
                nameText.text.toString(),
                descText.text.toString(),
                modules
            )

            //TODO: how to send board above to activity
            startActivity(Intent(this, BoardDetailsActivity::class.java))
        }
    }
}
