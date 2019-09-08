package isel.pt.unicommunity.presentation.common

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class ProgressObs<T>(private val progressBar: OptionalProgressBar, val f : (T)->Unit) : Observer<T> {

    override fun onChanged(t: T) {
        f(t)
        progressBar.stop()
    }
}

class OptionalProgressBar(activity: AppCompatActivity, starter: (() -> Unit)? = null ){

    private var act : ProgressBarActivity? = null

    init {
        if(activity is ProgressBarActivity)
            act = activity


        if (starter!=null) {
            starter()
            start()
        }
    }

    fun start() = act?.startProgressBar()

    fun stop() = act?.stopProgressBar()


}

interface ProgressBarActivity {

    fun startProgressBar()

    fun stopProgressBar()

}