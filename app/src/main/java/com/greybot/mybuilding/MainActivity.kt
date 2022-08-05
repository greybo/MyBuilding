package com.greybot.mybuilding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.greybot.mybuilding.mask.MaskedEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val phoneInput = findViewById<MaskedEditText>(R.id.phone_input)
        phoneInput.setOnClickListener {

        }
//        val inputTextLayout = findViewById<TextInputLayout>(R.id.inputTextLayout)
//        val editText = findViewById<TextInputEditText>(R.id.inputEditText)
//        editText.setOnClickListener {
//            Toast.makeText(this, "tapped on area", Toast.LENGTH_SHORT).show()
//        }

//        val inputTextLayout2 = findViewById<TextInputLayout>(R.id.inputTextLayout2)
//        inputTextLayout2.error = "Text error"
    }
}