package com.greybot.mycosts.components.items

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greybot.mycosts.models.AdapterItems

@Composable
fun ItemSpaceComponent(model: AdapterItems.SpaceItem) {
    Spacer(modifier = Modifier.height(15.dp))
}

@Preview
@Composable
fun PreviewItemSpaceComponent() {
    ItemSpaceComponent(
        model = AdapterItems.SpaceItem(-1),
    )
}