package com.example.bloodglucose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)


        val todayButton: Button = findViewById(R.id.button_today_profile)
        todayButton.setOnClickListener {
            val todayIntent = Intent(this@ProfileActivity, TodayActivity::class.java)
            startActivity(todayIntent)
        }

        val sugarButton: Button = findViewById(R.id.button_sugar_level_profile)
        sugarButton.setOnClickListener {
            val sugarIntent = Intent(this@ProfileActivity, SugarGraphActivity::class.java)
            startActivity(sugarIntent)
        }

        val calendarButton: Button = findViewById(R.id.button_calendar_profile)
        calendarButton.setOnClickListener {
            val calendarIntent = Intent(this@ProfileActivity, CalendarActivity::class.java)
            startActivity(calendarIntent)
        }

        val glucoseButton: Button = findViewById(R.id.button_glucose_profile)
        glucoseButton.setOnClickListener {
            val glucoseIntent = Intent(this@ProfileActivity, GlucoseActivity::class.java)
            startActivity(glucoseIntent)
        }

        val productButton: Button = findViewById(R.id.button_lst_profile)
        productButton.setOnClickListener {
            val productIntent = Intent(this@ProfileActivity, ProductActivity::class.java)
            startActivity(productIntent)
        }
    }

}