package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class GlucoseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.glucose)

        val backButton: Button = findViewById(R.id.button_glucose_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@GlucoseActivity, ProfileActivity::class.java)
            startActivity(backIntent)
        }
    }

}