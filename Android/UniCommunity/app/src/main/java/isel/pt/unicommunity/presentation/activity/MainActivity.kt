package isel.pt.unicommunity.presentation.activity

import android.os.Bundle
import android.util.Log
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.presentation.fragment.*
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.presentation.fragment.board.AllBoardsFragment
import isel.pt.unicommunity.presentation.viewmodel.MainActivityViewModel
import isel.pt.unicommunity.presentation.viewmodel.BackStackManagingViewModel
import isel.pt.unicommunity.testing.fragmentTesting.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    BackStackManagingActivity {




    lateinit var viewModel : BackStackManagingViewModel
    lateinit var auxVm : MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val app = getUniCommunityApp()

        viewModel = getViewModel("MainActivity"){
            BackStackManagingViewModel(R.id.container)//TODO double bangs onde e que ha mesmo a verificaçao
        }

        auxVm = getViewModel("auxViewModel"){ MainActivityViewModel(app) }
        val navUrl = intent.getStringExtra("navHref")



        auxVm.fetchNav(navUrl)


        auxVm.navigation.observe(this, Observer { navigationInputDto ->


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



            /*homeFragment= HomeFragment()
            allBoardsFragment = AllBoardsFragment()
            myBoardsFragment= MyBoardsFragment()
            profile = ProfileFragment()*/

            nav_view.menu.forEach { it.isVisible = false }

            val links = navigationInputDto._links

            if(links.home != null) {
                auxVm.homeFragment = HomeFragment(links.home)
                (nav_view as NavigationView).menu.findItem(R.id.nav_home).isVisible = true
            }
            if(links.allBoards != null) {
                auxVm.allBoardsFragment = AllBoardsFragment(links.allBoards)
                (nav_view as NavigationView).menu.findItem(R.id.nav_all_boards).isVisible = true
            }


            if(links.userProfile != null) {
                auxVm.profile = ProfileFragment(links.userProfile)
                (nav_view as NavigationView).menu.findItem(R.id.nav_profile).isVisible = true
            }

            initialNavigation(savedInstanceState)

            nav_view.setNavigationItemSelectedListener(this)

        })
    }



    private fun initialNavigation(savedInstanceState: Bundle?) {
        /*if (savedInstanceState != null) {
            val starter = savedInstanceState.getString("starter")
            if (starter != null) {
                val frag = auxVm.getStarter(starter)
                if (frag != null)
                    navigateTo(frag)
            }
        } else {*/
            nav_view.menu.forEach {
                if(it.isVisible){
                    val starter = auxVm.getStarter(it.title.toString())
                    if(starter!=null){
                        navigateTo(starter)
                            it.isChecked = true
                            return
                    }
                }
            }
            /*val frag = auxVm.getStarter("Home")
            if (frag != null) // should always be true TODO --> not true, o getSingleForumLink poode nao vir na resposta
                navigateTo(frag)*/
        //}
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        else
            if(!viewModel.navigateBack(supportFragmentManager))
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

                navigateTo(auxVm.homeFragment,reseting = true)
/*

                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    homeFragment
                ).commit()
*/
                /*

                Toast.makeText(this,"camera", Toast.LENGTH_LONG).show()
                Navigation.createNavigateOnClickListener(R.id.__testing__a_fragment)*/

            }
            R.id.nav_my_boards -> {
                Log.v("testing", "camera")
                navigateTo(auxVm.myBoardsFragment, reseting = true)
/*
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    allBoardsFragment
                ).commit()
*/
            }
            R.id.nav_all_boards -> {
                Log.v("testing", "camera")
                navigateTo(auxVm.allBoardsFragment, reseting = true)

/*
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    myBoardsFragment
                ).commit()
*/
            }
            R.id.nav_profile -> {
                navigateTo(auxVm.profile, reseting = true)
/*
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    profile
                ).commit()
*/
            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun navigateTo(fragment: Fragment, inBackStack: Boolean, reseting : Boolean){
        viewModel.navigateTo(fragment, supportFragmentManager, inBackStack, reseting)
    }



}

interface BackStackManagingActivity {

    fun navigateTo(fragment: Fragment, inBackStack : Boolean = true, reseting : Boolean =false)

}


/*private class FragmentMapper(val app: AppCompatActivity, val containerId: Int) {

    /*
    * "self": { "href": "/" },
        "/rels/home": { "href": "/home" },
        "/rels/logout": { "href": "/logout" },
        "/rels/userProfile": { "href": "/users/100/profile" },
        "/rels/allBoards": { "href": "/boards" },
        "/rels/myBoards": { "href": "/myBoards" }

    * */

    fun map(getSingleForumLink: String): SideNavItem? {

        return when(getSingleForumLink){
            "/rels/home" -> SideNavItem("Home", switchBuilder(HomeFragment(links.home.href)))
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
*/
private class SideNavItem(
    val name: String,
    //val resourceId : Int,
    val onClick: () -> Unit

)