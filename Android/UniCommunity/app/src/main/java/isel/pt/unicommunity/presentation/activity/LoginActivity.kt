package isel.pt.unicommunity.presentation.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import isel.pt.unicommunity.BASEURL
import isel.pt.unicommunity.R
import isel.pt.unicommunity.common.OptionalProgressBar
import isel.pt.unicommunity.common.ProgressBarActivity
import isel.pt.unicommunity.common.ProgressObs
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.LoginLink
import isel.pt.unicommunity.presentation.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), ProgressBarActivity {
    lateinit var progress : ProgressDialog

    override fun startProgressBar() {
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog*/
    }

    override fun stopProgressBar() {
        progress.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val app = getUniCommunityApp()
        val viewModel = getViewModel("Login"){

            LoginViewModel(app, LoginLink("$BASEURL/signin")) //todo tirar isto

        }

        progress =  ProgressDialog(this)

        loginBtn.isClickable=true

        val sharedPref = getSharedPreferences(app.SharedPreferences_FileName, Context.MODE_PRIVATE)

        val savedEmail = sharedPref.getString("email", null)
        val savedPw = sharedPref.getString("pw", null)

        if(savedEmail != null && savedPw !=null){
            email.setText(savedEmail)
            password.setText(savedPw)
            rememberMe.isChecked = true
        }



        loginBtn.setOnClickListener {
            app.email = email.text.toString()
            app.password = password.text.toString()

            //deactivate button
            loginBtn.isClickable=false
            progress.show()

            viewModel.tryLogin(
                app.email,
                app.password
            )
        }

        val progressBar = OptionalProgressBar(this)



        viewModel.loginLd.observe(
            this,
            ProgressObs(progressBar) {

                Toast.makeText(this, "FUCKING YEET", Toast.LENGTH_SHORT).show()
                loginBtn.isClickable=true


                if(rememberMe.isChecked){
                    val edit =
                        getSharedPreferences(app.SharedPreferences_FileName, Context.MODE_PRIVATE).edit()

                    edit.putString("email", app.email)
                    edit.putString("pw", app.password)
                    edit.apply()
                }




                if(it._links.nav!=null) {
                    val intent =Intent(this, MainActivity::class.java)
                    intent.putExtra("navHref", it._links.nav.href)
                    this.startActivity(intent)
                }
                else if(it._links.getMultipleBoardsLink != null){
                    val intent =Intent(this, SimpleActivity::class.java)
                    intent.putExtra("boardsHref", it._links.getMultipleBoardsLink.href)
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
