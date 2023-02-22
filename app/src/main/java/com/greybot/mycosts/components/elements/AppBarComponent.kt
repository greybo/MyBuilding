package com.greybot.mycosts.components.elements

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greybot.mycosts.present.folder.preview.ActionButtonType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCostsToolbar(
    title: String? = null,
    navigation: ActionButtonType? = null,
    action: ActionButtonType? = null,
    callback: ((ActionButtonType) -> Unit)? = null
) {
    TopAppBar(
        title = {
            title?.let { Text(it, fontSize = 22.sp) }
        },
        navigationIcon = {
            navigation?.let {
                IconButton(onClick = {
                    callback?.invoke(it)
                }) { Icon(imageVector = it.icon, tint = it.color, contentDescription = "Home") }
            }

        },
        actions = {
//            Spacer(Modifier)
            action?.let {
                IconButton(onClick = {
                    callback?.invoke(it)
                }) { Icon(imageVector = it.icon, tint = it.color, contentDescription = "Menu") }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.White),
        modifier = Modifier.shadow(4.dp, shape = RectangleShape)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewToolbar() {
    MyCostsToolbar("META-NIT.COM")
}