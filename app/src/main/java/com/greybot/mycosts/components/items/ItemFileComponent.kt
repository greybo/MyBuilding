package com.greybot.mycosts.components.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.models.MeasureType
import com.greybot.mycosts.present.adapter.AdapterCallback

@Composable
fun ItemFileComponent(model: AdapterItems.RowItem, callback: (AdapterCallback) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 8.dp)
        ) {
            Checkbox(
                checked = model.isBought, onCheckedChange = { callback.invoke(AdapterCallback.RowBuy(model)) },
                modifier = Modifier.size(25.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    callback.invoke(AdapterCallback.RowName(model))
                }
        ) {
            Text(
                text = model.name,
                modifier = Modifier.padding(top = 12.dp, end = 16.dp, bottom = 12.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewFileComponent() {
    ItemFileComponent(
        model = getFakeFile(),
        callback = {}
    )
}

fun getFakeFile(): AdapterItems.RowItem {
    return AdapterItems.RowItem(
        "",
        "Test",
        "count: 0",
        MeasureType.None,
        0.0,
        0.0,
        isBought = true
    )
}
