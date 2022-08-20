package com.greybot.mycosts.utility

import android.animation.Animator
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

fun ExtendedFloatingActionButton.animateHideFab(isHide: Boolean, callback: (() -> Unit)? = null) {
    val fab = this
    fab.post {
        val fabBottomMargin = (fab.layoutParams as ConstraintLayout.LayoutParams).bottomMargin
        val target = fab.height.toFloat() + fabBottomMargin

        if (isHide) {
            fab.animate()
                .translationY(target)
                .setInterpolator(LinearInterpolator())
                .setDuration(100)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationEnd(animation: Animator?) {
                        callback?.invoke()
                    }

                    override fun onAnimationStart(animation: Animator?) {}
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationRepeat(animation: Animator?) {}
                }).start()
        } else {
            fab.hide()
            fab.translationY = target
            fab.requestLayout()
            fab.animate()
                .translationY(0f)
                .setInterpolator(LinearInterpolator())
                .setDuration(100)
                .setStartDelay(300)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {
                        fab.show()
                    }

                    override fun onAnimationEnd(animation: Animator?) {}
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationRepeat(animation: Animator?) {}
                }).start()
        }
    }
}