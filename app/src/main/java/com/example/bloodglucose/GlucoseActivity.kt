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
import com.example.databaseinterface.Database
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.FieldValue


val USER_ID = "TestUser"

val glucoseLevels = ArrayList<Int>()

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

                // Add the glucose level to the array
                glucoseLevels.add(glucoseLevel)

                // Store the array in Firebase
                val database = Firebase.firestore

                val ref = database.collection("users").document(USER_ID)

                // create a hashmap of values to be uploaded to the database
                val measurement = hashMapOf(
                    "datetime" to FieldValue.serverTimestamp(),
                    "value" to glucoseLevel
                )


                val myRef = ref.collection("glucoseRecords")
                    .add(measurement)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "Glucose_record added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding Glucose_record", e)
                    }

                // myRef.setValue(glucoseLevels)

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
}