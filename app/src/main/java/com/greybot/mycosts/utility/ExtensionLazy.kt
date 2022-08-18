package com.greybot.mycosts.utility

import androidx.fragment.app.Fragment
import com.greybot.mycosts.MainActivity


inline fun <reified T> Fragment.getRouter(): Lazy<T> {
    return lazy {
        val activity = (requireActivity() as? MainActivity) ?: throw Throwable()
        activity.router as T
    }
}