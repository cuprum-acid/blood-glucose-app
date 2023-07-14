package com.example.bloodglucose

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SeekBar
import android.widget.Spinner
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddExerciseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_exer)

        val backButton: Button = findViewById(R.id.button_add_exer_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@AddExerciseActivity, ListExercisesActivity::class.java)
            startActivity(backIntent)
        }

        val spinner: Spinner = findViewById(R.id.spinner_add_exer)
        ArrayAdapter.createFromResource(
            this,
            R.array.add_exer,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val inputExercise: TextInputEditText = findViewById(R.id.glucose_level_edit_text)


        val caloriesSeekBar: SeekBar = findViewById(R.id.seekBar_calories_add_pr)

        val submitButton: Button = findViewById(R.id.button_submit_exer)


        // when the user presses "submit" button
        submitButton.setOnClickListener {
            var selectedProductType = spinner.selectedItem.toString()
            val enteredExercise = inputExercise.text.toString()

            val caloriesValue = caloriesSeekBar.progress

            // Store the data in Firebase
            val database = Firebase.firestore

            val ref = database.collection("users").document(USER_ID)


            selectedProductType =
                if (selectedProductType == "Power exercises") {
                    "power"
                } else if (selectedProductType == "Static exercises") {
                    "static"
                } else {
                    "cardio"
                }


            // create a hashmap of values to be uploaded to the database
            val exerciseItem = hashMapOf(
                "calories" to caloriesValue,
                "category" to selectedProductType,
            )




            ref.collection("userAddedExercises")
                .document(enteredExercise)
                .set(exerciseItem)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "Exercise added added with ID: ${enteredExercise}")
                    val backIntent = Intent(this@AddExerciseActivity, ProductActivity::class.java)
                    startActivity(backIntent)
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding new exercise", e)
                }


        }
    }
}