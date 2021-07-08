package com.appstyx.authtest.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat
import android.widget.TextView

import android.view.ViewGroup

import android.graphics.Color
import android.view.View

import android.widget.ArrayAdapter
import com.appstyx.authtest.R


//todo change password
const val PASSWORD = "MyPass"

/**
 * Check for network availability
 *
 * @param context Activity context
 * @return true if network available else false.
 * @see   <a href="https://stackoverflow.com/a/53532456/6478047">stackOverflow</a>
 */
fun isNetworkAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
   /* } else {
        @Suppress("Deprecation")
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }*/

    return result
}

fun getColor(context: Context, colorRes: Int): Int {
    return ContextCompat.getColor(context, colorRes)
}


/**
 * Common spinner adapter , pass list of any items and make sure to override toString of the object to show what you like
 * adopted from https://android--code.blogspot.com/2015/08/android-spinner-hint.html
 */
fun <T> getSpinnerWithHintAdapter(context: Context,spinnerItems:List<T>,hintColor:Int=Color.GRAY):ArrayAdapter<T>{
    val spinnerArrayAdapter: ArrayAdapter<T> = object : ArrayAdapter<T>(context, R.layout.spinner_item,spinnerItems) {
        override fun isEnabled(position: Int): Boolean {
            return position != 0  //disable the hint
        }

        override fun getDropDownView(
            position: Int, convertView: View?,
            parent: ViewGroup?
        ): View {
            val view: View = super.getDropDownView(position, convertView, parent)
            val tv = view as TextView?
            if (position == 0) {
                // Set the hint text color gray
                tv?.setTextColor(hintColor)
            } else {
                tv?.setTextColor(getColor(context,R.color.text_color_default))
            }
            return view
        }
    }

    return spinnerArrayAdapter
}





