package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ListMedsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_med)

        val backButton: Button = findViewById(R.id.button_back_meds)
        backButton.setOnClickListener {
            val backIntent = Intent(this@ListMedsActivity, ProductActivity::class.java)
            startActivity(backIntent)
        }

        val nextButton: Button = findViewById(R.id.button_next_meds)
        nextButton.setOnClickListener {
            val nextIntent = Intent(this@ListMedsActivity, ListExercisesActivity::class.java)
            startActivity(nextIntent)
        }

        val plusButton: Button = findViewById(R.id.button_plus_meds)
        plusButton.setOnClickListener {
            val plusIntent = Intent(this@ListMedsActivity, AddMedsActivity::class.java)
            startActivity(plusIntent)
        }

    }
}