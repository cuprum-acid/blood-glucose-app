package com.example.bloodglucose

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.core.content.ContextCompat.getSystemService
import com.example.bloodglucose.databinding.GlucoseBinding
import com.example.databaseinterface.Database


class GlucoseActivity : AppCompatActivity() {
    private lateinit var binding: GlucoseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GlucoseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonSubmitGlucose.setOnClickListener { Database.getInstance("app/src/main/java/com/example/bloodglucose/DataBase.db").submitGlucose(binding.glucoseLevelEditText.text.toString().toDouble()) }
        binding.glucoseLevelEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }


        val backButton: Button = findViewById(R.id.button_glucose_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@GlucoseActivity, ProfileActivity::class.java)
            startActivity(backIntent)
        }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}