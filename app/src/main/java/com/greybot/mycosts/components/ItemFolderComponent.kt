package com.greybot.mycosts.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
fun ItemFolderComponent(model: AdapterItems.FolderItem, callback: (AdapterCallback) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable { callback.invoke(AdapterCallback.FolderOpen(model)) },
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp,
    ) {
        Column {
            Row {
                Text(text = model.name,
                    style = MyCostsTypography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .padding(8.dp, 0.dp, 0.dp, 0.dp)
                        .align(Alignment.CenterVertically)
                        )
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(text = model.countInner)
                    Text(text = model.total)
                }
            }
//        Divider(
//            modifier = Modifier
//                .padding(8.dp, 0.dp, 8.dp, 2.dp)
//                .align(Alignment.CenterHorizontally),
//            thickness = 1.5.dp
//        )
        }
    }
}

@Preview
@Composable
fun PreviewItemFolder() {
    ItemFolderComponent(
        model = AdapterItems.FolderItem("Name Test", "", "count: 0", "total: 0"),
        callback = {})
}