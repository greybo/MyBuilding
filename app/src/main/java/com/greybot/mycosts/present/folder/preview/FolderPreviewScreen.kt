package com.greybot.mycosts.present.folder.preview

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.greybot.mycosts.components.toolbar.ActionButtonType
import com.greybot.mycosts.components.toolbar.MyCostsToolbar
import com.greybot.mycosts.present.adapter.AdapterComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderPreviewScreen(
    viewModel: FolderPreviewViewModel = viewModel(),
) {
    val context = LocalContext.current
    val itemsList by viewModel.state.observeAsState()
    val toolbarMode by viewModel.toolbarModelLiveData.observeAsState()

    val isDelete = (toolbarMode?.rightAction?.type == ActionButtonType.Delete)

    Scaffold(
        topBar = {
            toolbarMode?.let { MyCostsToolbar(it) }
        },
        snackbarHost = {

        },
        content = {
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxSize()
            ) {

                AdapterComponent(
                    list = itemsList,
                    isDelete,
                    callback = viewModel::handleAdapterClick
                )
//                BottomSheetComponent()

            }
        }
    )
}