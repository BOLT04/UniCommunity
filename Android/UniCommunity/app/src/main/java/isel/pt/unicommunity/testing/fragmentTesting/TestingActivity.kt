package isel.pt.unicommunity.testing.fragmentTesting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import isel.pt.unicommunity.BackStackManagingActivity
import isel.pt.unicommunity.testing.EmptyAFActivity
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.testing.fragmentTesting.fragment.FragmentA
import isel.pt.unicommunity.testing.fragmentTesting.fragment.FragmentB
import isel.pt.unicommunity.testing.fragmentTesting.fragment.FragmentC
import isel.pt.unicommunity.testing.fragmentTesting.model.TestingViewModel
import kotlinx.android.synthetic.main.activity_main.*

class TestingActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    BackStackManagingActivity {

    lateinit var A : Fragment
    lateinit var B: Fragment
    lateinit var C: Fragment


    lateinit var viewModel : TestingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

         viewModel = getViewModel("Testing"){
            TestingViewModel(/*id!!*/)//TODO double bangs onde e que ha mesmo a verificaçao
        }


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        A= FragmentA()
        B= FragmentB()
        C= FragmentC()


        nav_view.setNavigationItemSelectedListener(this)

        navigateTo(A)
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
                navigateTo(A)

            }
            R.id.nav_my_boards -> {
                Log.v("testing", "camera")
                navigateTo(B)
            }
            R.id.nav_all_boards -> {
                Log.v("testing", "camera")
                navigateTo(C)
            }
            R.id.nav_profile -> {
                startActivity(Intent(this, EmptyAFActivity::class.java))


            }
            R.id.nav_share -> {
            }
            R.id.nav_send -> {
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun navigateTo(fragment: Fragment, inBackStack: Boolean, reseting: Boolean) {
        viewModel.navigateTo(fragment, supportFragmentManager, inBackStack, reseting)
    }

    /*fun goto( navigator: Retriever<User>){

        val app = application as UniCommunityApp
        val TAG = "yes"
        app.queue.add(
            navigator.get<User>(
                Response.Listener {
                //textView.text = it.name
                Log.v(TAG, "gotcha")

                },
                Response.ErrorListener {
                    textView.text = it.localizedMessage
                    Log.v(TAG, ""+it.message)
                    Log.v(TAG, ""+it.localizedMessage)
                    Log.v(TAG, ""+it)
                }
            )
        )


    }*/

}
