package app.telda.task

import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import app.telda.task.ui.main.MainActivity
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchEdittextTest : TestCase() {

    @get:Rule
    var rule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun test_removeTextFromSearch() {
        val activity = rule.activity
        activity.runOnUiThread {
           val searchET =  activity!!.findViewById<EditText>(R.id.et_search)!!
            searchET.setText("test")
            activity.findViewById<ImageView>(R.id.img_clear)!!.performClick()
            //assert(searchET.text.toString() == "test")
            assert(searchET.text.isEmpty())
        }
    }
}