package com.greybot.mycosts.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun FabComponent(
    icons: Any = Icons.Rounded.Add,
    text: String? = null,
    containerColor: Color = Color.Blue,
    contentColor: Color = Color.White,
    expanded: Boolean = false,
    callback: () -> Unit = {}
) {

    FloatingActionButton(
        onClick = { callback() },
        containerColor = containerColor,
        content = {
            Row {
                getImagePainter(icons)?.let {
                    Icon(
                        painter = it,
                        contentDescription = "",
                        tint = contentColor,
                        modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp)
                    )
                }
                AnimatedVisibility(visible = expanded) {
                    Text(
                        text = text ?: "",
                        color = contentColor,
                        modifier = Modifier
                            .padding(0.dp, 8.dp, 16.dp, 8.dp)
                            .align(Alignment.CenterVertically)
                    )
                }

            }
        },
        elevation = FloatingActionButtonDefaults.elevation(10.dp)
    )
}

@Composable
fun getImagePainter(icons: Any): Painter? {
    return when (icons) {
        is ImageVector -> rememberVectorPainter(icons)
        is ImageBitmap -> BitmapPainter(icons)
        is Int -> painterResource(id = icons)
        else -> null
    }
}
