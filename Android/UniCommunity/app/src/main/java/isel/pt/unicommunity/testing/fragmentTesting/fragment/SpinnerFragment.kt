package isel.pt.unicommunity.testing.fragmentTesting.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import isel.pt.unicommunity.R
import isel.pt.unicommunity.testing.fragmentTesting.TestingActivity

class SpinnerFragment : Fragment(){

    val TAG = "TESTING"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.__testing__spinner_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()

        val activity = (activity as TestingActivity)

        Log.v("testing", "bing")

        //spinner.isIndeterminate=true

        activity.viewModel.isDone.observe(
            activity,
            androidx.lifecycle.Observer {
                Log.v("testing", "bong")

                if(it)
                    activity.navigateTo(FragmentC())
            }
        )



    }

}