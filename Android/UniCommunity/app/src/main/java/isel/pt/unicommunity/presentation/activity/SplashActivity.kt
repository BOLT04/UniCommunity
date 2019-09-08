package isel.pt.unicommunity.presentation.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import isel.pt.unicommunity.R
import isel.pt.unicommunity.SERVER_ENTRY_POINT
import isel.pt.unicommunity.presentation.common.OptionalProgressBar
import isel.pt.unicommunity.presentation.common.ProgressBarActivity
import isel.pt.unicommunity.presentation.common.ProgressObs
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.presentation.viewmodel.HomeViewModel

class SplashActivity : AppCompatActivity(), ProgressBarActivity{
        private lateinit var progress : ProgressDialog

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

            val app = getUniCommunityApp()
            val viewModel = getViewModel("Home"){

                HomeViewModel(app, SERVER_ENTRY_POINT)

            }

            progress =  ProgressDialog(this)


            val progressBar = OptionalProgressBar(this){
                viewModel.fetchHome()
            }

            viewModel.homeLD.observe(
                this,
                ProgressObs(progressBar) {

                    val login = it._links.login
                    if (login!=null){
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.putExtra("loginUrl", login.href)
                        startActivity(intent)
                    }

                },
                ProgressObs(progressBar) {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            )
        }

}