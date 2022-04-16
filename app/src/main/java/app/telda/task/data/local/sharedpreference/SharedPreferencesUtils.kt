package app.telda.task.data.local.sharedpreference

import android.content.Context
import app.telda.task.utils.extensions.getSharedPref

class SharedPreferencesUtils private constructor(private val prefHelper: PrefHelper) {

    companion object {

        private var sharedPreferencesUtils: SharedPreferencesUtils? = null

        fun getInstance(context: Context): SharedPreferencesUtils {
            if (sharedPreferencesUtils == null)
                sharedPreferencesUtils = SharedPreferencesUtils(PrefHelper(context.getSharedPref()))

            return sharedPreferencesUtils!!
        }
    }
}