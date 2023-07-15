package com.example.bloodglucose

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import com.example.bloodglucose.databinding.GlucoseBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.FieldValue
import android.graphics.Color


val USER_ID = "TestUser"


class GlucoseActivity : AppCompatActivity() {
    private lateinit var binding: GlucoseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GlucoseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSubmitGlucose.setOnClickListener {
            val glucoseLevelText = binding.glucoseLevelEditText.text.toString()
            if (glucoseLevelText.isNotEmpty()) {
                val glucoseLevel = glucoseLevelText.toInt()

                // Store the array in Firebase
                val database = Firebase.firestore

                val ref = database.collection("users").document(USER_ID)

                // Create a hashmap of values to be uploaded to the database
                val measurement = hashMapOf(
                    "datetime" to FieldValue.serverTimestamp(), "value" to glucoseLevel
                )


                ref.collection("glucoseRecords").add(measurement)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "Glucose_record added with ID: ${documentReference.id}")
                    }.addOnFailureListener { e ->
                        Log.w(TAG, "Error adding Glucose_record", e)
                    }

                // myRef.setValue(glucoseLevels)

                analyzeGlucose(glucoseLevel)

                // Clear the input field
                binding.glucoseLevelEditText.text?.clear()
            }
        }
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


    private fun analyzeGlucose(glucoseLevel: Int) {
        if (glucoseLevel < 4) {
            displayColoredAlert("Low glucose level", Color.RED)
        } else if (glucoseLevel > 8) {
            displayColoredAlert("High glucose level", Color.RED)
        } else {
            displayColoredAlert("Normal glucose level", Color.GREEN)
        }
    }

    private fun displayColoredAlert(message: String, color: Int) {
        binding.glucoseAlert.text = message
        binding.glucoseAlert.setTextColor(color)
    }


}