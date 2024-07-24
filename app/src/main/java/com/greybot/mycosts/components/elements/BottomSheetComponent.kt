package com.greybot.mycosts.components.elements

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.present.adapter.IRowCost
import com.greybot.mycosts.utility.hideKeyboard
import com.greybot.mycosts.utility.showKeyboard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetComponent(type: AdapterCallback?) {
    val context = LocalContext.current
    val state = (type as? IRowCost)?.value ?: return
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = false
    )
    var isSheetFullScreen by remember { mutableStateOf(false) }
    val focusRequesterCount = remember { FocusRequester() }
    val focusRequesterPrice = remember { FocusRequester() }

    val roundedCornerRadius = 12.dp// if (isSheetFullScreen) 0.dp else 12.dp
    val modifier = if (isSheetFullScreen)
        Modifier.fillMaxSize()
    else
        Modifier.fillMaxWidth()

    LaunchedEffect(modalSheetState.currentValue) {
        if (modalSheetState.currentValue == ModalBottomSheetValue.Hidden) {
            context.hideKeyboard()
        } else {
            when (type) {
                is AdapterCallback.RowCount -> {
                    focusRequesterCount.requestFocus()
                }
                is AdapterCallback.RowPrice -> {
                    focusRequesterPrice.requestFocus()
                }
                else -> TODO()
            }
            context.showKeyboard()
        }
    }

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(
            topStart = roundedCornerRadius,
            topEnd = roundedCornerRadius
        ),
        sheetContent = {
            Column(
                modifier = modifier,//Modifier.fillMaxSize(),//
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {

//                Button(
//                    onClick = {
//                        isSheetFullScreen = !isSheetFullScreen
//                    }
//                ) {
//                    Text(text = "Toggle Sheet Fullscreen")
//                }
//
//                Button(
//                    onClick = {
//                        coroutineScope.launch { modalSheetState.hide() }
//                    }
//                ) {
//                    androidx.compose.material.Text(text = "Hide Sheet")
//                }

                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    val label1 = "Кількість"
                    val label2 = "Ціна"
                    var field_1 by remember { mutableStateOf(state.count.toString()) }
                    var field_2 by remember { mutableStateOf(state.price.toString()) }

                    OutlinedTextField(
                        value = field_1,
                        singleLine = true,
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth(0.35f)
                            .focusRequester(focusRequesterCount),
                        onValueChange = { field_1 = it },
                        label = { Text(label1) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { context.hideKeyboard() }
                        ),
                    )

                    OutlinedTextField(
                        value = field_2,
                        singleLine = true,
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth(0.5f)
                            .focusRequester(focusRequesterPrice),
                        onValueChange = { field_2 = it },
                        label = { androidx.compose.material.Text(label2) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { context.hideKeyboard() }
                        ),
                    )
                }
            }
        },
        content = {

        }
    )
}

@Preview
@Composable
fun PreviewBottomSheetComponent() {
    BottomSheetComponent(null)
}