package com.greybot.mycosts.present.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.base.RouterCallbackHandler
import com.greybot.mycosts.databinding.ExploreFragmentBinding
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.theme.MyCostsTheme
import com.greybot.mycosts.utility.getRouter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainExploreFragment :
    BaseBindingFragment<ExploreFragmentBinding>(ExploreFragmentBinding::inflate) {

    private val viewModel by viewModels<MainExploreViewModel>()
    private val router: IMainExploreRouter by getRouter()
    private val routerHandler: RouterCallbackHandler by lazy { RouterCallbackHandler(router) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.setContent {
            MyCostsTheme {
                MainExampleScreen(viewModel, ::handleOnClick)
            }
        }

        viewModel.fetchData()
    }

    private fun handleOnClick(callback: AdapterCallback) {
        routerHandler.invoke(callback)
    }

//    override fun onResume() {
//        super.onResume()
////        binding.exploreFloatButton.animateShowFab()
//    }
}

