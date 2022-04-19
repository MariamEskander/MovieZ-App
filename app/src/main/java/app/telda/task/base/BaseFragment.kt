package app.telda.task.base

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import app.telda.task.R
import app.telda.task.utils.extensions.observe
import com.google.android.material.snackbar.Snackbar


abstract class BaseFragment : Fragment() {

    private var pd: Dialog? = null

    abstract fun getViewModel(): BaseViewModel?

    abstract fun setObservers()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createCustomProgressDialog()
        onViewReady()
        initListeners()
        setObservers()
    }

    private fun initListeners() {
        observe(getViewModel()?.showNetworkError) {
            showErrorMsg(getString(R.string.check_internet_connection))
        }
    }

    abstract fun onViewReady()

    fun showErrorMsg(msg: String?) {
        activity.let {
            if (!msg.isNullOrEmpty()) {
                val snackBar = Snackbar.make(requireActivity().findViewById(android.R.id.content), "", Snackbar.LENGTH_SHORT)
                val layout = snackBar.view as Snackbar.SnackbarLayout
                layout.setBackgroundColor(Color.TRANSPARENT)
                val textView = layout.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
                textView.visibility = View.INVISIBLE
                val snackView: View = layoutInflater.inflate(R.layout.error_message, null)
                val textViewTop = snackView.findViewById(R.id.tv_msg) as TextView
                textViewTop.text = msg
                layout.addView(snackView, 0)
                snackBar.show()
            }

        }
    }

    private fun createCustomProgressDialog() {
        context?.let {
            pd = Dialog(it, R.style.DialogCustomTheme)
            pd?.setContentView(R.layout.layout_dialog_progress)
            pd?.setCancelable(false)
        }
    }

    fun showDialogLoading() {
        pd?.let {
            if (!it.isShowing)
                it.show()
        }
    }

    fun hideDialogLoading() {
        pd?.let {
            if (it.isShowing)
                it.dismiss()
        }
    }
}