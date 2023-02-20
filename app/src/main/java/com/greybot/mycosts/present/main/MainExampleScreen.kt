package com.greybot.mycosts.present.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.greybot.mycosts.R


@Composable
fun MainExampleScreen(viewModel: MainExploreViewModel = viewModel()) {
        Text(
            text = "Hello compose!",
            color = colorResource(id = R.color.black)
        )
}