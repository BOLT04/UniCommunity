package isel.pt.unicommunity

import android.os.Bundle
import android.util.Log
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import isel.pt.unicommunity.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.RuntimeException

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var omeFragment : Fragment
    lateinit var allBoardsFragment: Fragment
    lateinit var myBoardsFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        omeFragment= HomeFragment()
        allBoardsFragment = AllBoardsFragment()
        myBoardsFragment= MyBoardsFragment()




        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                Log.v("testing", "camera")

                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    omeFragment
                ).commit()

                /*

                Toast.makeText(this,"camera", Toast.LENGTH_LONG).show()
                Navigation.createNavigateOnClickListener(R.id.a_fragment)*/

            }
            R.id.nav_my_boards -> {
                Log.v("testing", "camera")

                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    allBoardsFragment
                ).commit()

            }
            R.id.nav_all_boards -> {
                Log.v("testing", "camera")

                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    myBoardsFragment
                ).commit()

            }
            R.id.nav_manage -> {

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


private class FragmentMapper(val app: AppCompatActivity, val containerId: Int) {

    /*
    * "self": { "href": "/" },
        "/rels/home": { "href": "/home" },
        "/rels/logout": { "href": "/logout" },
        "/rels/userProfile": { "href": "/users/100/profile" },
        "/rels/allBoards": { "href": "/boards" },
        "/rels/myBoards": { "href": "/myBoards" }

    * */

    fun map(link: String): SideNavItem? {

        return when(link){
            "/rels/home" -> SideNavItem("Home", switchBuilder(HomeFragment()))
            "/rels/logout" -> SideNavItem("Logout", switchBuilder(LogoutFragment()))
            "/rels/userProfile" -> SideNavItem("Profile", switchBuilder(ProfileFragment()))
            "/rels/allBoards" -> SideNavItem("All Boards", switchBuilder(AllBoardsFragment()))
            "/rels/myBoards" -> SideNavItem("My Boards", switchBuilder(MyBoardsFragment()))
            else -> null
        }

    }




    fun switchBuilder(fragment: Fragment): () -> Unit = {
        app.supportFragmentManager
            .beginTransaction()
            .replace(
                containerId,
                fragment
            ).commit()
    }

}

private class SideNavItem(
    val name: String,
    //val resourceId : Int,
    val onClick: () -> Unit

)