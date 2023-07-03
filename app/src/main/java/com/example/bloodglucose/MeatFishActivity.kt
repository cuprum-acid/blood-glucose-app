package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MeatFishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.m_and_f)

        val backButton: Button = findViewById(R.id.button_back_meat_fish_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@MeatFishActivity, ProductActivity::class.java)
            startActivity(backIntent)
        }
    }
}