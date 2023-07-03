package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class VegsFruitsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.v_and_f)

        val backButton: Button = findViewById(R.id.button_vegs_fruits_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@VegsFruitsActivity, ProductActivity::class.java)
            startActivity(backIntent)
        }
    }
}