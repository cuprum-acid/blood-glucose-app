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
            val backIntent = Intent(this@ListExercisesActivity, ListMedsActivity::class.java)
            startActivity(backIntent)
        }

        val nextButton: Button = findViewById(R.id.button_next_exer)
        nextButton.setOnClickListener {
            val nextIntent = Intent(this@ListExercisesActivity, ProfileActivity::class.java)
            startActivity(nextIntent)
        }

        val plusButton: Button = findViewById(R.id.button_plus_exer)
        plusButton.setOnClickListener {
            val plusIntent = Intent(this@ListExercisesActivity, AddExerciseActivity::class.java)
            startActivity(plusIntent)
        }

        val cardioButton: Button = findViewById(R.id.button_cardio)
        cardioButton.setOnClickListener {
            val cardioIntent = Intent(this@ListExercisesActivity, CardioExerActivity::class.java)
            startActivity(cardioIntent)
        }

        val staticButton: Button = findViewById(R.id.button_static)
        staticButton.setOnClickListener {
            val staticIntent = Intent(this@ListExercisesActivity, StaticExerActivity::class.java)
            startActivity(staticIntent)
        }

        val powerButton: Button = findViewById(R.id.button_power)
        powerButton.setOnClickListener {
            val powerIntent = Intent(this@ListExercisesActivity, PowerExerActivity::class.java)
            startActivity(powerIntent)
        }

    }
}