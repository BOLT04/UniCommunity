package isel.pt.unicommunity.testing.fragmentTesting.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import isel.pt.unicommunity.R

class FragmentC : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.__testing__c_fragment, container, false)
    }
    


}