package com.appstyx.authtest.common

import android.app.Activity
import android.app.Dialog
import android.content.Context

/**
 * Created by Manohar on 5/6/21.
 */

private var dialog: Dialog? = null

fun showProgress(context: Context?, cancelable: Boolean = false): Dialog? {
    if (context is Activity && (dialog == null || !dialog!!.isShowing)) {
        try {

            context.runOnUiThread {
                dialog = CustomProgressDialog(context)
                dialog?.setCancelable(cancelable)
                dialog?.show()

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return dialog
}

fun dismissProgress() {
    try {
        if (dialog != null && dialog!!.isShowing) {
            dialog?.dismiss()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    dialog = null
}
