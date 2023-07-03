package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AddMedsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_med)

        val backButton: Button = findViewById(R.id.button_add_meds_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@AddMedsActivity, ListMedsActivity::class.java)
            startActivity(backIntent)
        }
    }
}