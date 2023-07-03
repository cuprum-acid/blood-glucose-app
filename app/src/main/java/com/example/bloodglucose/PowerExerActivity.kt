package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PowerExerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.power_exercises)

        val backButton: Button = findViewById(R.id.button_power_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@PowerExerActivity, ListExercisesActivity::class.java)
            startActivity(backIntent)
        }
    }
}