package isel.pt.unicommunity.presentation.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import isel.pt.unicommunity.presentation.common.OptionalProgressBar
import isel.pt.unicommunity.presentation.common.ProgressBarActivity
import isel.pt.unicommunity.presentation.common.ProgressObs
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.LoginLink
import isel.pt.unicommunity.presentation.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import isel.pt.unicommunity.R


class LoginActivity : AppCompatActivity(), ProgressBarActivity {
    lateinit var progress : ProgressDialog

    override fun startProgressBar() {
        progress.show()
    }

    override fun stopProgressBar() {
        progress.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginurl = intent.getStringExtra("loginUrl")

        val app = getUniCommunityApp()
        val viewModel = getViewModel("Login"){
            LoginViewModel(app, LoginLink(loginurl)) //todo tirar isto
        }

        progress =  ProgressDialog(this)
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog*/

        loginBtn.isClickable=true

        val sharedPref = getSharedPreferences(app.SharedPreferences_FileName, Context.MODE_PRIVATE)

        val savedEmail = sharedPref.getString("email", null)
        val savedPw = sharedPref.getString("pw", null)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)




        if (savedEmail != null && savedPw != null) {
            email.setText(savedEmail)
            password.setText(savedPw)
            rememberMe.isChecked = true
        }


        fun login(){
            app.email = email.text.toString()
            app.password = password.text.toString()

            //deactivate button
            loginBtn.isClickable = false
            startProgressBar()

            viewModel.tryLogin(
                app.email,
                app.password
            )
        }


        loginBtn.setOnClickListener {
            login()
        }

        if(isLoggedIn)
            login()



        val progressBar = OptionalProgressBar(this)

        viewModel.loginLd.observe(
            this,
            ProgressObs(progressBar) {

                Toast.makeText(this, "FUCKING YEET", Toast.LENGTH_SHORT).show()
                loginBtn.isClickable=true

                val edit =
                    getSharedPreferences(app.SharedPreferences_FileName, Context.MODE_PRIVATE).edit()
                edit.putBoolean("isLoggedIn", true)
                if(rememberMe.isChecked){


                    edit.putString("email", app.email)
                    edit.putString("pw", app.password)
                    edit.apply()
                }




                if(it._links.nav!=null) {
                    val intent =Intent(this, MainActivity::class.java)
                    intent.putExtra("navHref", it._links.nav.href)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    this.startActivity(intent)
                }
                else if(it._links.getMultipleBoardsLink != null){
                    val intent =Intent(this, SimpleActivity::class.java)
                    intent.putExtra("boardsHref", it._links.getMultipleBoardsLink.href)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    this.startActivity(intent)
                }

            },
            ProgressObs(progressBar) {

                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                loginBtn.isClickable=true
            }
        )
    }





}
