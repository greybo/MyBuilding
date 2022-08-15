package com.greybot.mybuilding.base

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import au.com.crownresorts.crma.extensions.setGoneOrVisible
import au.com.crownresorts.crma.extensions.setTextOrGone
import com.greybot.mybuilding.R

abstract class AbstractActivity<Router> : AppCompatActivity(), BaseActivityListener {

    lateinit var navController: NavController
    lateinit var navHost: NavHostFragment
    abstract val router: Router
    abstract val graphId: Int
//    abstract fun setRouter()

    var isDeepLink = false

    override fun onCreate(savedInstanceState: Bundle?) {
//        setTheme(R.style.RootTabActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment).let {
            navHost = it
            navHost.navController.graph = navHost.navController.navInflater.inflate(graphId)
            navController = it.navController
        }
//        setRouter()
    }

    override fun closePressed() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override var isProgressShow: Boolean = false

    override fun showOrHideProgress(isShow: Boolean, message: MessageLoader?) {
        isProgressShow = isShow
        findViewById<LinearLayout>(R.id.activityProgressBarParent).setGoneOrVisible(isShow)
        findViewById<TextView>(R.id.activityProgressBarTitle).setTextOrGone(message?.title)
        findViewById<TextView>(R.id.activityProgressBarText).setTextOrGone(message?.text)
    }
}