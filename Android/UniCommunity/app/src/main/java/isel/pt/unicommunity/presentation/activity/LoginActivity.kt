package isel.pt.unicommunity.presentation.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import isel.pt.unicommunity.BASEURL
import isel.pt.unicommunity.MainActivity
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.presentation.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val app = getUniCommunityApp()
        val viewModel = getViewModel("Login"){
            LoginViewModel(app)
        }

        //val sharedPref = getSharedPreferences(app.SharedPreferences_FileName, Context.MODE_PRIVATE)

        /*ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog*/

        val progress = ProgressDialog(this)
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog*/

        viewModel.loginIsOk.observe(this, androidx.lifecycle.Observer {
            Toast.makeText(this, "FUCKING YEET", Toast.LENGTH_SHORT).show()



            if(it._links.nav!=null) {
                val intent =Intent(this, MainActivity::class.java)
                intent.putExtra("navHref", it._links.nav.href)
                this.startActivity(intent)
            }
            else mock()//TODO tirar isto daqui é so para testar agora
        })

        loginBtn.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()

            //deactivate button
            loginBtn.isClickable=false
            progress.show()

            viewModel.tryLogin(
                "$BASEURL/signin",
                email,
                password,
                Response.Listener {
                    app.email = it.email
                    app.username = it.name
                    app.password = password
                    viewModel.loginIsOk.value = it

                    Log.v("LOGIN", "success")
                    progress.dismiss()
                    loginBtn.isClickable=true
                },
                Response.ErrorListener {
                    Log.v("LOGIN", "failure $it")

                    progress.dismiss()
                    loginBtn.isClickable=true
                    /*TODO do something here*/
                }
            )
        }
    }

    private fun mock() {
        val intent =Intent(this, MainActivity::class.java)
        intent.putExtra("navUrl", "$BASEURL/navigation")
        this.startActivity(intent)
    }

    /*private fun restoreUserInputTexts(model: LoginViewModel, sharedPref: SharedPreferences) {

       sharedPref.getString("email",null)
       sharedPref.getString("password",null)

        if (model.textOrganization == "")
            model.textOrganization = sharedPref.getString(getString(R.string.organizationId), "")

        if (model.textToken == "")
            model.textToken = sharedPref.getString(getString(R.string.userToken), "")

        login_userID.text = Editable.Factory().newEditable(model.textUser)
        login_orgID.text = Editable.Factory().newEditable(model.textOrganization)
        login_personalToken.text = Editable.Factory().newEditable(model.textToken)
    }*/



}
