package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TodayProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.today_products)

        val backButton: Button = findViewById(R.id.button_today_products_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@TodayProductsActivity, TodayActivity::class.java)
            startActivity(backIntent)
        }
    }
}