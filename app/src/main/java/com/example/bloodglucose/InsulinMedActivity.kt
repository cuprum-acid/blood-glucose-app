package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class InsulinMedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insulin)

        val backButton: Button = findViewById(R.id.button_back_insulin)
        backButton.setOnClickListener {
            val backIntent = Intent(this@InsulinMedActivity, ListMedsActivity::class.java)
            startActivity(backIntent)
        }
    }
}