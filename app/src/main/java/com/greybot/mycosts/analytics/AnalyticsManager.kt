package com.greybot.mycosts.analytics

import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import com.greybot.mycosts.CostsApplication


class AnalyticsManager {
    private var mFirebaseAnalytics: FirebaseAnalytics? = CostsApplication.share.mFirebaseAnalytics

    fun sendAnalytics(id: String, name: String) {
        val bundle = bundleOf(
            FirebaseAnalytics.Param.ITEM_ID to id,
            FirebaseAnalytics.Param.ITEM_NAME to name,
            FirebaseAnalytics.Param.CONTENT_TYPE to "image"
        )
        mFirebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }
}