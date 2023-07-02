package com.example.bloodglucose

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_list)

        val backButton: Button = findViewById(R.id.button_back_add_pr)
        backButton.setOnClickListener {
            val backIntent = Intent(this@ProductActivity, ProfileActivity::class.java)
            startActivity(backIntent)
        }

    }
}