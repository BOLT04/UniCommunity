package isel.pt.unicommunity.testing.viewmodeltesting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import kotlinx.android.synthetic.main.__testing__viewmodel.*

class ZActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.__testing__viewmodel)
    }

    override fun onStart() {
        super.onStart()

        val app = getUniCommunityApp()

        if (app.commonVm.get("A") == null) {
            app.commonVm["A"]= A("2")
        }

        val vmA = app.commonVm["A"]!! as A

        val vmB = getViewModel("B"){
            B("2")
        }

        val vmC = getViewModel("C"){
            C("2")
        }



        textViewA.text = vmA.stringValue
        textViewB.text = vmB.stringValue
        textViewC.text = vmC.stringValue


        buttonA.setOnClickListener {
            vmA.stringValue+=" 2"
            textViewA.text = vmA.stringValue
        }

        buttonB.setOnClickListener {
            vmB.stringValue+=" 2"
            textViewB.text = vmB.stringValue
        }

        buttonC.setOnClickListener {
            vmC.stringValue+=" 2"
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