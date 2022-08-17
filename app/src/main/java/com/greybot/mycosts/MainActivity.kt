package com.greybot.mycosts

import android.os.Bundle
import androidx.activity.viewModels
import com.greybot.mycosts.base.AbstractActivity
import com.greybot.mycosts.base.CoroutinesViewModel
import com.greybot.mycosts.utility.LogApp

class MainActivity : AbstractActivity<AppRouter>() {

    private val viewModel by viewModels<CoroutinesViewModel>()

    override val router: AppRouter by lazy { AppRouter(navController) }
    override val graphId: Int = R.navigation.nav_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel.newSingleThreadTest()
//        viewModel.actorTest()
//        viewModel.errorTest1()
//        viewModel.errorTest2()
//        viewModel.errorTest3()

        viewModel.errorsStream.observe(this) {
            LogApp.e("MainActivity", it, "run in onCreate")
        }
    }

    override fun backPressed() {
        TODO("Not yet implemented")
    }

}