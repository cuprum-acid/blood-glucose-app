package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CardioExerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cardio_exercises)

        val backButton: Button = findViewById(R.id.button_back_cardio)
        backButton.setOnClickListener {
            val backIntent = Intent(this@CardioExerActivity, ListExercisesActivity::class.java)
            startActivity(backIntent)
        }
    }
}