package com.greybot.mycosts.components.items

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.present.folder.preview.ButtonType
import com.greybot.mycosts.theme.MyCostsTypography

@Composable
fun ItemButtonsComponent(model: AdapterItems.ButtonAddItem, callback: (AdapterCallback) -> Unit) {

    OutlinedButton(
        onClick = { callback.invoke(AdapterCallback.AddButton(model)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Text(
            text = model.type.row,
            style = MyCostsTypography.titleMedium,
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center
        )
    }
}


@Preview
@Composable
fun PreviewItemButtonsComponent() {
    ItemButtonsComponent(
        model = AdapterItems.ButtonAddItem(ButtonType.Row),
        callback = {})
}