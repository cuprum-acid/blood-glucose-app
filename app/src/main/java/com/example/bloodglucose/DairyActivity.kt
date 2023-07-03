package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DairyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dairy)

        val backButton: Button = findViewById(R.id.button_back_dairy)
        backButton.setOnClickListener {
            val backIntent = Intent(this@DairyActivity, ProductActivity::class.java)
            startActivity(backIntent)
        }
    }
}