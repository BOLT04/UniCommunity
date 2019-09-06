package isel.pt.unicommunity.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.GetMultipleReportsLink
import isel.pt.unicommunity.presentation.adapter.OnClickListener
import isel.pt.unicommunity.presentation.adapter.ReportAdapter
import isel.pt.unicommunity.presentation.adapter.ReportView
import isel.pt.unicommunity.presentation.common.OptionalProgressBar
import isel.pt.unicommunity.presentation.common.ProgressObs
import isel.pt.unicommunity.presentation.viewmodel.ReportViewModel
import kotlinx.android.synthetic.main.fragment_all_comments.*

class ReportFragment (val getMultipleReportsLink: GetMultipleReportsLink) : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_all_comments, container, false)


    override fun onStart() {
        super.onStart()

        val appCompatActivity = activity as AppCompatActivity
        val app = appCompatActivity.getUniCommunityApp()


        val viewModel = appCompatActivity
            .getViewModel("reports${getMultipleReportsLink.href}"){
                ReportViewModel(app, getMultipleReportsLink)
            }

        comments_rv.layoutManager = LinearLayoutManager(appCompatActivity)


        val progressBar = OptionalProgressBar(appCompatActivity){
            viewModel.fetchReports()
        }


        val onClickListener = object : OnClickListener<ReportView> {
            override fun onClick(value: ReportView) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }


        viewModel.reportsLd.observe(
            this,
            ProgressObs(progressBar){
                comments_rv.adapter = ReportAdapter(
                    it,
                    onClickListener
                )
            },
            ProgressObs(progressBar){
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        )




    }




}