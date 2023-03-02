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

//    Card(modifier = Modifier.fillMaxWidth(1f)) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Checkbox(
                checked = model.isBought, onCheckedChange = {},
                modifier = Modifier.size(25.dp)
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = model.name,
                modifier = Modifier
                    .clickable {
                        callback.invoke(AdapterCallback.RowName(model))
                    }
//                    .align(Alignment.CenterVertically)
            )

        }

    }
//    }
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
        0.0
    )
}
