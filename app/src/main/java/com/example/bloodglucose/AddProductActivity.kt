package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AddProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_pr)

        val backButton: Button = findViewById(R.id.button_add_products_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@AddProductActivity, ProductActivity::class.java)
            startActivity(backIntent)
        }
    }
}