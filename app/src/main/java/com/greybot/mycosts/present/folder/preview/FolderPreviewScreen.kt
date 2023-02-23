package com.greybot.mycosts.present.folder.preview

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
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
        snackbarHost = {

        },
        content = {
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxSize()
            ) {

                AdapterComponent(list = itemsList, callback = handleAdapterClick)
//                BottomSheetComponent()

            }
        }
    )
}