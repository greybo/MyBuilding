package com.greybot.mycosts.present.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.greybot.mycosts.base.BaseBindingFragment
import com.greybot.mycosts.databinding.ExploreFragmentBinding
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.theme.MyCostsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainExploreFragment :
    BaseBindingFragment<ExploreFragmentBinding>(ExploreFragmentBinding::inflate) {

    private val viewModel by viewModels<MainExploreViewModel>()
    private val router: MainExploreRouter by lazy { MainExploreRouter(findNavController()) }

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
        router.invoke(callback)
    }
}

