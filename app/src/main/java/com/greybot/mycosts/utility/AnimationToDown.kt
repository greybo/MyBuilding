package com.greybot.mycosts.utility

import android.animation.Animator
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

fun ExtendedFloatingActionButton.animateShowFab() {
    animateFabVisibility(false)
}

fun ExtendedFloatingActionButton.animateFabHide(callback: (() -> Unit)? = null) {
    animateFabVisibility(true, callback)
}

private fun ExtendedFloatingActionButton.animateFabVisibility(
    isHide: Boolean,
    callback: (() -> Unit)? = null
) {
    val fab = this
    fab.post {
        val fabBottomMargin = (fab.layoutParams as ConstraintLayout.LayoutParams).bottomMargin
        val target = fab.height.toFloat() + fabBottomMargin

        if (isHide) {
            fab.animate()
                .translationY(target)
                .setInterpolator(LinearInterpolator())
                .setDuration(100)
                .setListener(
                    object : Animator.AnimatorListener {
                        override fun onAnimationStart(p0: Animator) {}
                        override fun onAnimationCancel(p0: Animator) {}
                        override fun onAnimationRepeat(p0: Animator) {}
                        override fun onAnimationEnd(p0: Animator) {
                            callback?.invoke()
                        }

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
                    override fun onAnimationStart(p0: Animator) {
                        fab.show()
                    }

                    override fun onAnimationEnd(p0: Animator) {}
                    override fun onAnimationCancel(p0: Animator) {}
                    override fun onAnimationRepeat(p0: Animator) {}
                }).start()
        }
    }
}