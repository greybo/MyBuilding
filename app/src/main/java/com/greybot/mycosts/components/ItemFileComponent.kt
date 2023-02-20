package com.greybot.mycosts.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.models.MeasureType
import com.greybot.mycosts.present.adapter.AdapterCallback

@Composable
fun ItemFileComponent(model: AdapterItems.RowItem, callback: (AdapterCallback) -> Unit) {

//    Card(modifier = Modifier.fillMaxWidth(1f)) {
        Row(modifier = Modifier.fillMaxWidth(1f)) {
            Column {
                Checkbox(checked = true, onCheckedChange = {}, modifier = Modifier.size(25.dp))
            }
            Column(modifier = Modifier.fillMaxWidth(1F)) {
                Text(text = model.name,
                    modifier = Modifier.clickable { callback.invoke(AdapterCallback.RowName(model)) })

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
