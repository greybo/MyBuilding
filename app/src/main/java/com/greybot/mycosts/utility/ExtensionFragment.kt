package com.greybot.mycosts.utility

import android.widget.Toast
import com.greybot.mycosts.BuildConfig
import com.greybot.mycosts.CostsApp


fun toast(message: String = "null") {
    if (BuildConfig.DEBUG)
        Toast.makeText(CostsApp.share.applicationContext, message, Toast.LENGTH_SHORT)
            .show()
}