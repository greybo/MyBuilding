package com.greybot.mycosts.components.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun BottomSheetComponent() {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.HalfExpanded)

    ModalBottomSheetLayout(
        sheetContent = {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(300.dp)
            ) {
                OutlinedTextField(
                    value = "userGuess",
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.75f),
                    onValueChange = {/*onUserGuessChanged*/ },
                    label = {
                        Text("Field")
//                            if (true/*isGuessWrong*/) {
//                                Text("stringResource(R.string.wrong_guess)")
//                            } else {
//                                Text("stringResource(R.string.enter_your_word)")
//                            }
                    },
//                        isError = isGuessWrong,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {  }
                    ),
                )
            }
        },
        sheetState = bottomSheetState,
    ) {
        OutlinedButton(onClick = {
            coroutineScope.launch {
                bottomSheetState.show()
            }
        }) {
            Text(text = "Show sheet dialog")
        }
    }
}

@Preview
@Composable
fun PreviewBottomSheetComponent() {
    BottomSheetComponent()
}