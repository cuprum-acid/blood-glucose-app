package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TodayMedsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.today_meds)

        val backButton: Button = findViewById(R.id.button_today_meds_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@TodayMedsActivity, TodayActivity::class.java)
            startActivity(backIntent)
        }
    }
}