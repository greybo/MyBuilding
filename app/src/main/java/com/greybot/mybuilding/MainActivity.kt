package com.greybot.mybuilding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inputTextLayout = findViewById<TextInputLayout>(R.id.inputTextLayout)
        val area = findViewById<EditText>(R.id.area_code)
        area.setOnClickListener {
            Toast.makeText(this, "tapped on area", Toast.LENGTH_SHORT).show()
        }
        inputTextLayout.error = "Text error"

        val inputTextLayout2 = findViewById<TextInputLayout>(R.id.inputTextLayout2)
        inputTextLayout2.error = "Text error"
    }
}