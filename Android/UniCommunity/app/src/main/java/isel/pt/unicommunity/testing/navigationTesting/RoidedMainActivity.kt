package isel.pt.unicommunity.testing.navigationTesting

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import isel.pt.unicommunity.R
import kotlinx.android.synthetic.main.activity_main.*

class RoidedMainActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    lateinit var hostFragment : NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        hostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment? ?: return

        setupSideNav()





    }


    private fun setupSideNav(){

        val sideNav = findViewById<NavigationView>(R.id.nav_view)
        val navGraphIds =
            listOf(R.navigation.allboards, R.navigation.myboards, R.navigation.profile)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                //hostFragment.navController.navigate(R.id.action_loginFragment_to_homeFragment)

            }
            R.id.nav_my_boards -> {
                Log.v("testing", "camera")
                //hostFragment..navController.navigate(R.navigation.myboards)

            }
            R.id.nav_all_boards -> {
                Log.v("testing", "camera")
                //navigateTo(myBoardsFragment)


            }
            R.id.nav_profile -> {
                //navigateTo(profile)
            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }




}