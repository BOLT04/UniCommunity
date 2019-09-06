package isel.pt.unicommunity.presentation.activity

import android.app.ProgressDialog
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import isel.pt.unicommunity.R
import isel.pt.unicommunity.presentation.common.ProgressBarActivity
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.presentation.fragment.*
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.presentation.fragment.board.AllBoardsFragment
import isel.pt.unicommunity.presentation.fragment.board.MyBoardsFragment
import isel.pt.unicommunity.presentation.viewmodel.MainActivityViewModel
import isel.pt.unicommunity.presentation.viewmodel.BackStackManagingViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    BackStackManagingActivity, ProgressBarActivity {


    lateinit var backstackVM : BackStackManagingViewModel
    lateinit var mainActivityVm : MainActivityViewModel

    lateinit var progress : ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val app = getUniCommunityApp()

        backstackVM = getViewModel("MainActivity"){
            BackStackManagingViewModel(R.id.container)
        }

        mainActivityVm = getViewModel("auxViewModel"){ MainActivityViewModel(app) }
        val navUrl = intent.getStringExtra("navHref")

        progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Wait while loading...")
        progress.setCancelable(false) // disable dismiss by tapping outside of the dialog*/



        mainActivityVm.fetchNav(navUrl)


        mainActivityVm.navigation.observe(
            this,
            Observer { navigationInputDto ->


                setContentView(R.layout.activity_main)

                setSupportActionBar(toolbar)

                val toggle = ActionBarDrawerToggle(
                    this, drawer_layout, toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close
                )
                drawer_layout.addDrawerListener(toggle)
                toggle.syncState()


                val navHeader  = nav_view.getHeaderView(0)
                val email = app.email
                val username = app.username

                navHeader.findViewById<TextView>(R.id.nav_header_email).text = email

                val headerUserName = navHeader.findViewById<TextView>(R.id.nav_header_name)

                if(username!=null) {
                    headerUserName.isVisible = true
                    headerUserName.text = username
                }else
                    headerUserName.isVisible=false

                nav_view.menu.forEach { it.isVisible = false }

                (nav_view as NavigationView).menu.findItem(R.id.nav_logout).isVisible = true






                val links = navigationInputDto._links

                if(links.allBoards != null) {
                    mainActivityVm.allBoardsFragment = AllBoardsFragment(links.allBoards)
                    (nav_view as NavigationView).menu.findItem(R.id.nav_all_boards).isVisible = true
                }

                if(links.myBoards != null) {
                    mainActivityVm.myBoardsFragment = MyBoardsFragment(links.myBoards)
                    (nav_view as NavigationView).menu.findItem(R.id.nav_my_boards).isVisible = true
                }


                if(links.userProfile != null) {
                    mainActivityVm.profile = ProfileFragment(links.userProfile)
                    (nav_view as NavigationView).menu.findItem(R.id.nav_profile).isVisible = true
                }


                nav_view.setNavigationItemSelectedListener(this)

                initialNavigation()

            },
            Observer {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        )
    }



    private fun initialNavigation() {
            nav_view.menu.forEach {
                if(it.isVisible){

                    it.isChecked = true
                    //nav_view.menu.performIdentifierAction(it.itemId, 0)
                    return
                    /*val starter = mainActivityVm.getStarter(it.title.toString())
                    if(starter!=null){
                        navigateTo(starter)
                        it.isChecked = true
                        return
                    }*/
                }
            }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START))
            drawer_layout.closeDrawer(GravityCompat.START)
        else
            if(!backstackVM.navigateBack(supportFragmentManager))
                super.onBackPressed()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify allBoardsLd parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {

            R.id.nav_logout -> mainActivityVm.logout(this)

            R.id.nav_my_boards -> navigateTo(mainActivityVm.myBoardsFragment, reseting = true)

            R.id.nav_all_boards -> navigateTo(mainActivityVm.allBoardsFragment, reseting = true)

            R.id.nav_profile -> navigateTo(mainActivityVm.profile, reseting = true)

            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    override fun navigateTo(fragment: Fragment, inBackStack: Boolean, reseting : Boolean){
        backstackVM.navigateTo(fragment, supportFragmentManager, inBackStack, reseting)
    }



    override fun startProgressBar() {
        progress.show()
    }

    override fun stopProgressBar() {
        progress.dismiss()
    }


}

interface BackStackManagingActivity {

    fun navigateTo(fragment: Fragment, inBackStack : Boolean = true, reseting : Boolean =false)

}



