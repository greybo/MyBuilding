package com.greybot.mycosts.present.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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

//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(
//                shape = CircleShape,
//                onClick = {},
//            ) {
//                Icon(imageVector = Icons.Filled.Add, contentDescription = "icon")
//            }
//        },
//        floatingActionButtonPosition = FabPosition.End,
////        isFloatingActionButtonDocked = true,
//        bottomBar = {
//            BottomAppBar(/*containerColor = Color.Cyan,*//* cutoutShape = CircleShape*/) {
//
//            }
//        }
//
//    ){
        Column {
            AdapterComponent(list, handleOnClick)

            FloatingActionButton(
                shape = CircleShape,
                onClick = { expanded = !expanded },
//                modifier = Modifier.align(Alignment.BottomEnd),
            ) {
                Row(Modifier.padding(start = 12.dp, end = 12.dp)) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    AnimatedVisibility(
                        expanded,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Text(modifier = Modifier.padding(start = 12.dp), text = "Favorite")
                    }
                }
            }
//            Spacer(Modifier.requiredHeight(20.dp))
//        }
//        it.calculateTopPadding()
    }

}