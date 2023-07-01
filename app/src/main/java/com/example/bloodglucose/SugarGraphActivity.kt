package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SugarGraphActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sugar_level_graph)

        val backButton: Button = findViewById(R.id.button_graph_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@SugarGraphActivity, ProfileActivity::class.java)
            startActivity(backIntent)
        }

        val exportButton: Button = findViewById(R.id.button_graph_export)

    }
}