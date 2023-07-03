package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class GroceryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grocery)

        val backButton: Button = findViewById(R.id.button_back_grocery)
        backButton.setOnClickListener {
            val backIntent = Intent(this@GroceryActivity, ProductActivity::class.java)
            startActivity(backIntent)
        }
    }
}