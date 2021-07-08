package com.appstyx.authtest.common

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.appstyx.authtest.R


class CustomProgressDialog : Dialog {
    private var dialog: Dialog? = null
    var imageView: ImageView? = null
        internal set
    internal var textView: TextView? = null


    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {
        init(context)
    }

    protected constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener) {
        init(context)
    }


    fun init(context: Context) {

        dialog = this

        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCancelable(false)


        val relativeLayout = RelativeLayout(context)
        val layoutParams =
            RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        relativeLayout.layoutParams = layoutParams


        val layoutParams_for_linear =
            RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams_for_linear.addRule(RelativeLayout.CENTER_IN_PARENT)
        val linearLayout = LinearLayout(context)
        linearLayout.layoutParams = layoutParams_for_linear
        linearLayout.orientation = LinearLayout.VERTICAL

        relativeLayout.addView(linearLayout)


        val layoutParams_Linear =
            LinearLayout.LayoutParams(
                250,
                250
            )
        layoutParams_Linear.gravity = Gravity.CENTER


//        imageView = ImageView(context)
//        imageView!!.setImageResource(R.drawable.progress_logo)
//        imageView!!.layoutParams = layoutParams_Linear

        val lottieAnimationView = LottieAnimationView(context)
        lottieAnimationView.setAnimation(R.raw.lottie_loading_little_girl_jumping)
        lottieAnimationView.repeatMode = LottieDrawable.RESTART
        lottieAnimationView.repeatCount = LottieDrawable.INFINITE
        lottieAnimationView.layoutParams = layoutParams_Linear
        lottieAnimationView.speed = 0.7f
        lottieAnimationView.playAnimation()

        linearLayout.addView(lottieAnimationView)

        textView = TextView(context)
        textView!!.layoutParams = layoutParams_for_linear
        textView!!.setTextColor(Color.parseColor("#ffffff"))
        textView!!.textSize = 18f
        linearLayout.addView(textView)

        val rotate = RotateAnimation(
            0f, 360f, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = 1500
        rotate.repeatCount = RotateAnimation.INFINITE
        rotate.interpolator = AccelerateDecelerateInterpolator()
        imageView?.startAnimation(rotate)


        dialog!!.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.window!!.setContentView(relativeLayout, layoutParams)
    }


    fun setProgressMessage(message: String): Dialog {

        if (textView != null)
            textView!!.text = message

        return this
    }

    fun setProgressMessageSize(size: Int): Dialog {

        if (textView != null)
            textView!!.textSize = size.toFloat()

        return this
    }

    fun setProgressMessageColour(colour: Int): Dialog {

        if (textView != null)
            textView!!.setTextColor(colour)

        return this
    }

    fun setIcon(resId: Int): Dialog {

        if (imageView != null) {
            imageView!!.setImageResource(resId)

        }

        return this
    }


}