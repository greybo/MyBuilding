package com.greybot.mybuilding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.greybot.mybuilding.base.CoroutinesViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<CoroutinesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.newSingleThreadTest()
    }
}