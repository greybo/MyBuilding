package com.greybot.mycosts.components.toolbar

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCostsToolbar(
    toolbarModel: ToolbarModel = ToolbarModel()
) {
    with(toolbarModel) {
        TopAppBar(
            title = {
                title?.let { Text(it, fontSize = 22.sp) }
            },
            navigationIcon = {
                homeAction?.let {
                    IconButton(onClick = {
                        callback.invoke(it.type)
                    }) { Icon(it.type.icon, contentDescription = "Home") }
                }

            },
            actions = {
//            Spacer(Modifier)
                rightAction?.let {
                    IconButton(onClick = {
                        callback.invoke(it.type)
                    }) { Icon(it.type.icon, contentDescription = "Menu") }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorsDefault.backgroundColor,
                titleContentColor = colorsDefault.contentColor,
                navigationIconContentColor = homeAction?.color ?: colorsDefault.contentColor,
                actionIconContentColor = rightAction?.color ?: colorsDefault.contentColor
            ),
            modifier = Modifier.shadow(4.dp, shape = RectangleShape)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewToolbar() {
    MyCostsToolbar(
        ToolbarModel(
            //        rightAction = null,
//        colorsDefault = toolbarColorDefault(Color.Blue, Color.White)
        )
    )
}