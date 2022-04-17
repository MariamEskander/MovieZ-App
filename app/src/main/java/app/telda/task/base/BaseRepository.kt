package app.telda.task.base


import app.telda.task.utils.ConnectivityUtils
import javax.inject.Inject


abstract class BaseRepository {

    @Inject
    lateinit var connectivityUtils: ConnectivityUtils

    fun isNetworkConnected(): Boolean {
        return connectivityUtils.isConnected()
    }

}