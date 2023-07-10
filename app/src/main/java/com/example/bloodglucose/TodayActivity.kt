package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TodayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.today)

        val backButton: Button = findViewById(R.id.button_home_today)
        backButton.setOnClickListener {
            val backIntent = Intent(this@TodayActivity, ProfileActivity::class.java)
            startActivity(backIntent)
        }

        val adviceButton: Button = findViewById(R.id.button_today_advice)
        adviceButton.setOnClickListener {
            val adviceIntent = Intent(this@TodayActivity, AdviceActivity::class.java)
            startActivity(adviceIntent)
        }

        val todayProductButton: Button = findViewById(R.id.button_today_products)
        todayProductButton.setOnClickListener {
            val todayProductIntent = Intent(this@TodayActivity, TodayProductsActivity::class.java)
            startActivity(todayProductIntent)
        }

        val medButton: Button = findViewById(R.id.button_today_meds)
        medButton.setOnClickListener {
            val medIntent = Intent(this@TodayActivity, TodayMedsActivity::class.java)
            startActivity(medIntent)
        }

        val exerciseButton: Button = findViewById(R.id.button_today_exercises)
        exerciseButton.setOnClickListener {
            val exerciseIntent = Intent(this@TodayActivity, TodayExercisesActivity::class.java)
            startActivity(exerciseIntent)
        }
    }
}