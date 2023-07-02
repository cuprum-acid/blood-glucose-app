package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TodayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.today)

        val homeButton: Button = findViewById(R.id.button_home_today)
        homeButton.setOnClickListener {
            val homeIntent = Intent(this@TodayActivity, ProfileActivity::class.java)
            startActivity(homeIntent)
        }

        val adviceButton: Button = findViewById(R.id.button_today_advice)
        adviceButton.setOnClickListener {
            val adviceIntent = Intent(this@TodayActivity, AdviceActivity::class.java)
            startActivity(adviceIntent)
        }
    }
}