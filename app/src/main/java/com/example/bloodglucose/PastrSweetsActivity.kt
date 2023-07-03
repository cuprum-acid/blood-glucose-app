package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PastrSweetsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.p_and_s)

        val backButton: Button = findViewById(R.id.button_pastr_sweets_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@PastrSweetsActivity, ProductActivity::class.java)
            startActivity(backIntent)
        }
    }
}