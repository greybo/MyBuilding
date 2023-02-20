package com.greybot.mycosts.present.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.present.adapter.AdapterComponent


@Composable
fun MainExampleScreen(
    viewModel: MainExploreViewModel = viewModel(),
    handleOnClick: (AdapterCallback) -> Unit
) {

    val list = viewModel.state.observeAsState().value

    AdapterComponent(list, handleOnClick)
}