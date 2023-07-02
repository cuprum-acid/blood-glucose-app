package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar)

        val backButton: Button = findViewById(R.id.button_calendar_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@CalendarActivity, ProfileActivity::class.java)
            startActivity(backIntent)
        }
    }
}