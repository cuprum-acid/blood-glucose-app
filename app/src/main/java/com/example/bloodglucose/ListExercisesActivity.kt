package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ListExercisesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_exer)

        val backButton: Button = findViewById(R.id.button_back_exer)
        backButton.setOnClickListener {
            val backIntent = Intent(this@ListExercisesActivity, ProductActivity::class.java)
            startActivity(backIntent)
        }

        val nextButton: Button = findViewById(R.id.button_next_exer)
        nextButton.setOnClickListener {
            val nextIntent = Intent(this@ListExercisesActivity, ProfileActivity::class.java)
            //TODO: add function to save data by this button
            startActivity(nextIntent)
        }

        val plusButton: Button = findViewById(R.id.button_plus_exer)
        plusButton.setOnClickListener {
            val plusIntent = Intent(this@ListExercisesActivity, AddExerciseActivity::class.java)
            startActivity(plusIntent)
        }

    }
}