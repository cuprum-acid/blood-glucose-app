package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class AddProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_pr)

        val backButton: Button = findViewById(R.id.button_add_products_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@AddProductActivity, ProductActivity::class.java)
            startActivity(backIntent)
        }

        val spinner: Spinner = findViewById(R.id.spinner_add_products)
        ArrayAdapter.createFromResource(
            this,
            R.array.add_pr,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }
}