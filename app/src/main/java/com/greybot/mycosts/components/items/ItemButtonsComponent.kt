package com.greybot.mycosts.components.items

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.theme.MyCostsTypography

@Composable
fun ItemButtonsComponent(model: AdapterItems.ButtonAddItem, callback: (AdapterCallback) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
//            .clickable { callback.invoke(AdapterCallback.AddButton(model)) },
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp,
    ) {
        OutlinedButton(onClick = { callback.invoke(AdapterCallback.AddButton(model)) }) {
            Text(
                text = model.type.row,
                style = MyCostsTypography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(8.dp, 0.dp, 8.dp, 0.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}


@Preview
@Composable
fun PreviewItemButtonsComponent() {
    ItemFolderComponent(
        model = AdapterItems.FolderItem("Name Test", "", "count: 0", "total: 0"),
        callback = {})
}