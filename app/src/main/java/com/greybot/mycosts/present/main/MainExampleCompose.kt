package com.greybot.mycosts.present.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.greybot.mycosts.components.FabComponent
import com.greybot.mycosts.components.toolbar.MyCostsToolbar
import com.greybot.mycosts.components.toolbar.ToolbarModel
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.present.adapter.AdapterComponent
import com.greybot.mycosts.utility.ROOT_FOLDER


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainExampleScreen(
    viewModel: MainExploreViewModel = viewModel(),
    handleOnClick: (AdapterCallback) -> Unit
) {

    val list = viewModel.state.observeAsState().value
    var expanded by remember { mutableStateOf(true) }


    Scaffold(
        topBar = {
            MyCostsToolbar(
                ToolbarModel(
                    title = "My Costs",
                    homeAction = null,
                    rightAction = null
                )
            )
        },
        floatingActionButton = {
            FabComponent(
                text = "Add",
                expanded = expanded,
                callback = { handleOnClick(AdapterCallback.FolderAdd(ROOT_FOLDER)) }
            )
        },
        content = {
            Column(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                AdapterComponent(list, false, handleOnClick)
            }
        })
}