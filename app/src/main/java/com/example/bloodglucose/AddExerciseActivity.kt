package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AddExerciseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_exer)

        val backButton: Button = findViewById(R.id.button_add_exer_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@AddExerciseActivity, ListExercisesActivity::class.java)
            startActivity(backIntent)
        }


    }
}