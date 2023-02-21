package com.greybot.mycosts.present.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.present.adapter.AdapterComponent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainExampleScreen(
    viewModel: MainExploreViewModel = viewModel(),
    handleOnClick: (AdapterCallback) -> Unit
) {

    val list = viewModel.state.observeAsState().value
    var expanded by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {

        AdapterComponent(list, handleOnClick)

        FloatingActionButton(
            shape = CircleShape,
            onClick = { expanded = !expanded },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(0.dp, 0.dp, 32.dp, 32.dp),
        ) {
            Row(Modifier.padding(start = 12.dp, end = 12.dp)) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = "Add",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                AnimatedVisibility(
                    expanded,
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(modifier = Modifier.padding(start = 12.dp, end = 12.dp), text = "Add")
                }
            }
        }
    }

}