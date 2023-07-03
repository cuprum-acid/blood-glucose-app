package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PillsMedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pills)

        val backButton: Button = findViewById(R.id.button_pills_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@PillsMedActivity, ListMedsActivity::class.java)
            startActivity(backIntent)
        }
    }
}