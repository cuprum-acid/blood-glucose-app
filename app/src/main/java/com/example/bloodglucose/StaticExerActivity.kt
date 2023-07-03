package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StaticExerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.static_exercises)

        val backButton: Button = findViewById(R.id.button_back_static)
        backButton.setOnClickListener {
            val backIntent = Intent(this@StaticExerActivity, ListExercisesActivity::class.java)
            startActivity(backIntent)
        }
    }
}