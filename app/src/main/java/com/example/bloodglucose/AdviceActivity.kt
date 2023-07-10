package com.example.bloodglucose

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AdviceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advice)

        val homeButton: Button = findViewById(R.id.button_home_advice)
        homeButton.setOnClickListener {
            val homeIntent = Intent(this@AdviceActivity, TodayActivity::class.java)
            startActivity(homeIntent)
        }
    }


}