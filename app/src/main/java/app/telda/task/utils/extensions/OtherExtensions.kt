package app.telda.task.utils.extensions


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*


fun Any?.toJsonString(): String = Gson().toJson(this)

fun <T> String?.toObjectFromJson(type: Type): T = Gson().fromJson(this, type)

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L?, body: (T) -> Unit) =
    liveData?.observe(this, Observer(body))

fun Long.toTime(): String {
    val format = SimpleDateFormat(" hh:mm a ", Locale.getDefault())
    return format.format(this)
}

fun Long.toDate(): String {
    val format = SimpleDateFormat("EEE, MMMM dd yyyy, HH:mm aa", Locale.getDefault())
    return format.format(this)
}

fun Long.toDateWithMonthAsString(): String {
    val format = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    return " ${format.format(this)}"
}

fun Long.toDate2(): String {
    val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return format.format(this)
}

fun Long.toDateMonthDay(): String {
    val format = SimpleDateFormat("dd∕MM", Locale.getDefault())
    return format.format(this)
}