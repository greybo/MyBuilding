package com.greybot.mycosts.components.elements

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarAppComponent(
    title: String? = null,
    homeAction: (() -> Unit)? = null,
    rightAction: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            title?.let { Text(it, fontSize = 22.sp) }
        },
        navigationIcon = {
            homeAction?.let {
                IconButton(onClick = {
                    it.invoke()
                }) { Icon(Icons.Default.ArrowBack, contentDescription = "Home") }
            }

        },
        actions = {
//            Spacer(Modifier)
            rightAction?.let {
                IconButton(onClick = {
                    it.invoke()
                }) { Icon(Icons.Filled.Menu, contentDescription = "Menu") }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.White),
        modifier = Modifier.shadow(4.dp, shape = RectangleShape)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewToolbar() {
    ToolbarAppComponent("META-NIT.COM", {}, {})
}