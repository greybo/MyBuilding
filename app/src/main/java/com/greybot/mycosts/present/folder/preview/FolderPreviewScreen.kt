package com.greybot.mycosts.present.folder.preview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
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
    ModalBottomSheet(onDismissRequest = { /*TODO*/ }) {

    }
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
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxSize()
            ) {

                AdapterComponent(list = itemsList, callback = handleAdapterClick)

                ModalBottomSheet(onDismissRequest = { /*TODO*/ }) {
                    OutlinedTextField(
                        value = "userGuess",
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {/*onUserGuessChanged*/ },
                        label = {
                            if (true/*isGuessWrong*/) {
                                Text("stringResource(R.string.wrong_guess)")
                            } else {
                                Text("stringResource(R.string.enter_your_word)")
                            }
                        },
//                        isError = isGuessWrong,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { /*onKeyboardDone()*/ }
                        ),
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    )
}