package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TodayExercisesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.today_exercises)

        val backButton: Button = findViewById(R.id.button_today_exercises_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@TodayExercisesActivity, TodayActivity::class.java)
            startActivity(backIntent)
        }
    }
}