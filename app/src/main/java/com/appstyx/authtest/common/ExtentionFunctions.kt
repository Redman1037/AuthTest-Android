package com.appstyx.authtest.common

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.androidadvance.topsnackbar.TSnackbar
import com.appstyx.authtest.BuildConfig
import com.appstyx.authtest.R
import com.google.android.material.snackbar.Snackbar
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.Placeholder
import com.bumptech.glide.Glide


fun Context.showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.showToast(
    message: CharSequence,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this.context, message, duration).show()
}


fun Any.log(message: String?) {
    if (BuildConfig.DEBUG)
        Log.i(this::class.java.simpleName, message ?: "")
}


enum class MessageType {
    SUCCESS,
    FAILURE,
    INFO
}

fun Fragment.onBackPressed(onBackPressed: () -> Unit) {
    val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed.invoke()
            }
        }
    requireActivity().onBackPressedDispatcher.addCallback(this, callback)
}


fun Fragment.showSnackBar(
    message: Any,
    messageType: MessageType = MessageType.INFO,
    duration: Int = Snackbar.LENGTH_LONG
) {

    activity?.showSnackBar(message, messageType, duration)
}


fun Activity.showSnackBar(
    message: Any,
    messageType: MessageType = MessageType.INFO,
    duration: Int = TSnackbar.LENGTH_LONG,
    view: View = this.window.decorView.rootView
) {

    var messageMain = message
    try {
        if (message is Int) {
            messageMain = getString(message)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    val snackbar = TSnackbar.make(view, messageMain.toString(), duration)
    val snackBarView = snackbar.view

    try {
        val params = snackBarView.layoutParams as FrameLayout.LayoutParams
        params.setMargins(25, 120, 25, 0)
        snackBarView.layoutParams = params
    } catch (e: Exception) {
        e.printStackTrace()
    }
    val textView =
        snackBarView.findViewById<TextView>(com.androidadvance.topsnackbar.R.id.snackbar_text)
    textView.setTextColor(Color.WHITE)

    textView.setOnClickListener { snackbar.dismiss() }

    val background: Int = when (messageType) {
        MessageType.SUCCESS -> R.drawable.rounded_success_green_radius_8dp
        MessageType.FAILURE -> R.drawable.rounded_error_red_radius_8dp
        else -> R.drawable.rounded_theme_radius_8dp
    }
    snackBarView.setBackgroundResource(background)
    snackbar.show()

}


fun Fragment.showAlert(
    message: String?,
    title: String? = null,
    cancelable: Boolean = false,
    positiveClickHandler: () -> Unit
): AlertDialog.Builder? {

    return activity?.showAlert(message, title, cancelable, positiveClickHandler)
}

fun Context.showAlert(
    message: String?,
    title: String? = null,
    cancelable: Boolean = false,
    positiveClickhandler: () -> Unit
): AlertDialog.Builder {

    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    if (title != null)
        builder.setTitle(title)
    if (message != null)
        builder.setMessage(message)

    builder.setPositiveButton(
        "Yes"
    ) { dialog, _ ->

        positiveClickhandler.invoke()
        dialog?.cancel()
    }

    builder.setNegativeButton(
        "Cancel"
    ) { dialog, _ ->
        dialog?.cancel()
    }

    builder.setCancelable(cancelable)

    builder.show()

    return builder
}


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.toggleVisibility() {
    if (visibility == View.VISIBLE)
        visibility = View.GONE
    else
        visibility = View.VISIBLE
}


fun Collection<Any>?.isNullOrEmpty(): Boolean {
    if (this == null || this.isEmpty()) {
        return true
    }
    return false
}

fun Collection<Any>?.isNotNullOrEmpty(): Boolean {
    if (this != null && this.isNotEmpty()) {
        return true
    }
    return false
}

fun Long?.isNullOrZero(): Boolean {
    if (this == null || this == 0L) {
        return true
    }
    return false
}

fun Long?.isNotNullOrZero(): Boolean {
    if (this != null && this != 0L) {
        return true
    }
    return false
}

fun <T> T.asList(): List<T> {
    val list = mutableListOf<T>()
    list.add(this)
    return list
}

fun ImageView.loadImage(url:String){
    Glide.with(context)
        .load(url)
        .into(this)
}
