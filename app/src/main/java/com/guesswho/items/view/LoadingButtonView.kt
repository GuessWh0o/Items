package com.guesswho.items.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.*
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.getDrawableOrThrow
import androidx.core.view.ViewCompat
import com.guesswho.items.R

/**
 *
 * @author Maxim on 2019-07-19
 */
class LoadingButtonView(context: Context, attributeSet: AttributeSet) :
    RelativeLayout(context, attributeSet) {

    private val btnKYC: RelativeLayout
    private val tvKYC: TextView
    private val ivKYC: ImageView

    private var bgColor = -1
    private var focusColor = -1

    init {
        inflate(context, R.layout.view_button_loading, this)

        btnKYC = this.findViewById(R.id.btn_loading)
        tvKYC = this.findViewById(R.id.tv_status)
        ivKYC = this.findViewById(R.id.scanner)


        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.LoadingButtonView,
            0, 0
        ).apply {
            try {
                btnKYC.background = this.getDrawableOrThrow(R.styleable.LoadingButtonView_buttonBg)
                bgColor = this.getColor(
                    R.styleable.LoadingButtonView_buttonColor,
                    ContextCompat.getColor(context, R.color.green)
                )
                focusColor = this.getColor(
                    R.styleable.LoadingButtonView_focusColor,
                    ContextCompat.getColor(context, R.color.button_pressed_green)
                )

                ViewCompat.setBackgroundTintList(ivKYC, ColorStateList.valueOf(bgColor))

                tvKYC.setTextColor(bgColor)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                this.recycle()
            }
        }

        this.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                btnKYC.background.setTint(focusColor)
            } else if (motionEvent.action == MotionEvent.ACTION_UP) {
                btnKYC.background.setTint(bgColor)
            }
            return@setOnTouchListener false
        }
    }

    fun animateButton() {
        tvKYC.text = context.getString(R.string.fetching_data)
        val animSet = AnimationSet(true)
        val animation = AnimationUtils.loadAnimation(context, R.anim.left_right)
        animSet.addAnimation(animation)
        ivKYC.startAnimation(animSet)
    }

    fun loadingCompletedNoData() {
        setColors()
        fadeOutLoadingButton()
    }

    fun loadingCompleted() {
        clearAnim()
        setColors()
        tvKYC.text = context.getString(R.string.delete_all)
        fadeInLoadingButton()
    }

    private fun setColors() {
        btnKYC.background = ContextCompat.getDrawable(context, R.drawable.button_green)
        btnKYC.background.setTint(bgColor)
        tvKYC.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
    }

    fun clearAnim() {
        ivKYC.clearAnimation()
        ivKYC.visibility = View.GONE
    }

    private fun fadeOutLoadingButton() {
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.interpolator = DecelerateInterpolator() as Interpolator?
        fadeOut.duration = 1000
        fadeOut.fillAfter = true
        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
                Log.d("Anim", "Animation repeated")
            }

            override fun onAnimationEnd(p0: Animation?) {
                btnKYC.visibility = View.GONE
            }

            override fun onAnimationStart(p0: Animation?) {
                Log.d("Anim", "Animation started")
            }
        })
        btnKYC.startAnimation(fadeOut)
    }

    private fun fadeInLoadingButton() {
        val fadeOut = AlphaAnimation(0f, 1f)
        fadeOut.interpolator = DecelerateInterpolator() as Interpolator?
        fadeOut.duration = 1000
        fadeOut.fillAfter = true
        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
                Log.d("Anim", "Animation repeated")
            }

            override fun onAnimationEnd(p0: Animation?) {
                btnKYC.visibility = View.VISIBLE
            }

            override fun onAnimationStart(p0: Animation?) {
                Log.d("Anim", "Animation started")
            }
        })
        btnKYC.startAnimation(fadeOut)
    }
}
