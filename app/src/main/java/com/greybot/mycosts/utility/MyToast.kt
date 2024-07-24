package com.greybot.mycosts.utility

import android.widget.Toast
import com.greybot.mycosts.CostsApp

var toastDebug: String = ""
    set(value) {
        Toast.makeText(
            CostsApp.share.applicationContext,
            value,
            Toast.LENGTH_SHORT
        ).show()
    }