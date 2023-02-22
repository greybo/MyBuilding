package com.greybot.mycosts

import android.os.Bundle
import com.greybot.mycosts.base.AbstractActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AbstractActivity() {

//    private val viewModel by viewModels<CoroutinesViewModel>()
    override val graphId: Int = R.navigation.nav_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel.newSingleThreadTest()
//        viewModel.actorTest()
//        viewModel.errorTest1()
//        viewModel.errorTest2()
//        viewModel.errorTest3()

//        viewModel.errorsStream.observe(this) {
//            LogApp.e("MainActivity", it, "run in onCreate")
//        }
    }

    override fun backPressed() {
        TODO("Not yet implemented")
    }

}