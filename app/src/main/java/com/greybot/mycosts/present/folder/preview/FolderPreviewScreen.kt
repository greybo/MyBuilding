package com.greybot.mycosts.present.folder.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.greybot.mycosts.components.elements.MyCostsToolbar
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.present.adapter.AdapterComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderPreviewScreen(
    viewModel: FolderPreviewViewModel = viewModel(),
    handleAdapterClick: (AdapterCallback) -> Unit
) {

    val itemsList by viewModel.state.observeAsState()
    val title by viewModel.title.observeAsState()
    val actionButtonType by viewModel.actionIconLiveData().observeAsState()

    Scaffold(
        topBar = {
            MyCostsToolbar(
                title,
                ActionButtonType.Back,
                actionButtonType,
                viewModel::handleOnClickOptionMenu
            )
        },
        content = {
            Column(modifier = Modifier.padding(top = it.calculateTopPadding()).fillMaxSize()) {
                AdapterComponent(list = itemsList, callback = handleAdapterClick)
            }
        }
    )
}