package isel.pt.unicommunity.testing.viewmodeltesting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import kotlinx.android.synthetic.main.__testing__viewmodel.*

class ZActivity1 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.__testing__viewmodel)
    }

    override fun onStart() {
        super.onStart()

        val app = getUniCommunityApp()

        val vmA = app.commonVm["A"] as A?

        val vmB = getViewModel("B"){
            B("1")
        }

        val vmC = getViewModel("C"){
            C("1")
        }



        textViewA.text = vmA?.stringValue
        textViewB.text = vmB.stringValue
        textViewC.text = vmC.stringValue


        buttonA.setOnClickListener {
            if (vmA != null) {
                vmA.stringValue+=" 1"
                textViewA.text = vmA?.stringValue
            }
        }

        buttonB.setOnClickListener {
            vmB.stringValue+=" 1"
            textViewB.text = vmB.stringValue
        }

        buttonC.setOnClickListener {
            vmC.stringValue+=" 1"
            textViewC.text = vmC.stringValue
        }



        act1btn.setOnClickListener {

            this.startActivity(Intent(this, ZActivity1::class.java))

        }

        act2btn.setOnClickListener {

            this.startActivity(Intent(this, ZActivity2::class.java))

        }


    }



}
