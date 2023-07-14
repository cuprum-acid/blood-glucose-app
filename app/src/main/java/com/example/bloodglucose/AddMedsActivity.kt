package com.example.bloodglucose

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddMedsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_med)

        val backButton: Button = findViewById(R.id.button_add_meds_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@AddMedsActivity, ListMedsActivity::class.java)
            startActivity(backIntent)
        }

        val spinner: Spinner = findViewById(R.id.spinner_add_meds)
        ArrayAdapter.createFromResource(
            this,
            R.array.add_med,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val inputMedication: TextInputEditText = findViewById(R.id.glucose_level_edit_text)


        val submitButton: Button = findViewById(R.id.button_add_meds_submit)


        // when the user presses "submit" button
        submitButton.setOnClickListener {
            var selectedMedicationType = spinner.selectedItem.toString()
            val enteredMedication = inputMedication.text.toString()


            // Store the data in Firebase
            val database = Firebase.firestore

            val ref = database.collection("users").document(USER_ID)


            selectedMedicationType =
                if (selectedMedicationType == "Pills") {
                    "pills"
                } else {
                    "insulin"
                }


            // create a hashmap of values to be uploaded to the database
            val medicationItem = hashMapOf(

                "category" to selectedMedicationType,
            )




            ref.collection("userAddedMedications")
                .document(enteredMedication)
                .set(medicationItem)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "Exercise added added with ID: ${enteredMedication}")
                    val backIntent = Intent(this@AddMedsActivity, ProductActivity::class.java)
                    startActivity(backIntent)
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding new exercise", e)
                }


        }
    }
}