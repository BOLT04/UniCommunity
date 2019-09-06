package isel.pt.unicommunity.testing.scroolTesting

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import isel.pt.unicommunity.R
import kotlinx.android.synthetic.main.fragment_forum_item_test.*

class ScrollingActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_forum_item_test)

        for (i in 1..100){
            val textView = TextView(this)
            textView.text="padding"
            linear.addView(textView)
        }

    }




}