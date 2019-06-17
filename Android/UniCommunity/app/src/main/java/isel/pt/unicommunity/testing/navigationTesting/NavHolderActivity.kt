package isel.pt.unicommunity.testing.navigationTesting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import isel.pt.unicommunity.R
import kotlinx.android.synthetic.main.__testing__navigation.*

class NavHolderActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.__testing__navigation)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment? ?: return

        val navController = host.navController

        A.setOnClickListener { view ->
            navController.navigate(R.id.action_fragmentA_to_fragmentC)
        }

        //A.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_fragmentA_self))
        //B.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_fragmentA_to_fragmentB))
        //C.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_fragmentA_to_fragmentC))



    }
}