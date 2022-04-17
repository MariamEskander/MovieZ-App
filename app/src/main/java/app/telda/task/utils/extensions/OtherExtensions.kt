package app.telda.task.utils.extensions


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import app.telda.task.R
import com.bumptech.glide.Glide
import com.google.gson.Gson
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}


inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)

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


fun String.toYear(): String {
    val sdf = SimpleDateFormat("yyyy", Locale.getDefault())
    return if (this.getTimeInMilliSec() != -1L)
        sdf.format(Date(this.getTimeInMilliSec()))
    else
        ""
}

fun String.toDate(): String {
    val sdf = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
    return if (this.getTimeInMilliSec() != -1L)
        sdf.format(Date(this.getTimeInMilliSec()))
    else
        ""
}

fun String.getTimeInMilliSec(): Long {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return try {
        val mDate = sdf.parse(this)
        mDate!!.time
    } catch (e: ParseException) {
        -1
    }
}

fun ImageView.loadImage(
    path: String?,
    @DrawableRes placeHolder: Int = R.drawable.placeholder
) {
    this.scaleType = ImageView.ScaleType.CENTER_CROP

    if (path.isNullOrEmpty())
        setImageResource(placeHolder)
    else {
        Glide.with(context)
            .load(path)
            .placeholder(
                context.getDrawableCompat(
                    placeHolder
                )
            ).thumbnail(0.05f).into(this)
    }
}


fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.hideView() {
    this.visibility = View.GONE
}
