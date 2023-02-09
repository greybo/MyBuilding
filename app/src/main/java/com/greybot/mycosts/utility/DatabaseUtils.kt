package com.greybot.mycosts.data.repository

import com.google.firebase.database.DataSnapshot

fun DataSnapshot.getBoolean(key: String, default: Boolean = false): Boolean {
    return (value as HashMap<*, *>)[key] as? Boolean ?: default
}