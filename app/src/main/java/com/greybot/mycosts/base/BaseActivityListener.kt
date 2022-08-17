package com.greybot.mycosts.base

interface BaseActivityListener {
    var isProgressShow: Boolean
    fun showOrHideProgress(isShow: Boolean, message: MessageLoader? = null)
    fun closePressed()
    fun backPressed()
}

open class MessageLoader(open val title: String, open val text: String)