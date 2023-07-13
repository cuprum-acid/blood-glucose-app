package com.example.bloodglucose

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.SeekBar
import android.util.Log
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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

        // initialize screen elements
        val inputProducts: TextInputEditText = findViewById(R.id.glucose_level_edit_text)

        val carbsSeekBar: SeekBar = findViewById(R.id.seekBar_carbohydrates_add_pr)
        val fatsSeekBar: SeekBar = findViewById(R.id.seekBar_fats_add_pr)
        val protsSeekBar: SeekBar = findViewById(R.id.seekBar_prots_add_pr)
        val caloriesSeekBar: SeekBar = findViewById(R.id.seekBar_calories_add_pr)

        val submitButton: Button = findViewById(R.id.button_add_products_submit)

        // when the user presses "submit" button
        submitButton.setOnClickListener {
            var selectedProductType = spinner.selectedItem.toString()
            val enteredProduct = inputProducts.text.toString()

            val carbsValue = carbsSeekBar.progress
            val fatsValue = fatsSeekBar.progress
            val protsValue = protsSeekBar.progress
            val caloriesValue = caloriesSeekBar.progress


            // Store the data in Firebase
            val database = Firebase.firestore

            val ref = database.collection("users").document(USER_ID)

            selectedProductType =
                if (selectedProductType == "Dairy products") {
                    "dairy"
                } else if (selectedProductType == "Meat and Fish") {
                    "meet and fish"
                } else if (selectedProductType == "Vegetables and Fruits") {
                    "vegetables and fruits"
                } else if (selectedProductType == "Grocery") {
                    "grocery"
                } else {
                    "pastries and sweets"
                }


            // create a hashmap of values to be uploaded to the database
            val foodItem = hashMapOf(
                "calories" to caloriesValue,
                "carbohydrates " to carbsValue,
                "category" to selectedProductType,
                "fats" to fatsValue,
                "proteins" to protsValue
            )




            ref.collection("userAddedFoods")
                .document(enteredProduct)
                .set(foodItem)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "Food added added with ID: ${enteredProduct}")
                    val backIntent = Intent(this@AddProductActivity, ProductActivity::class.java)
                    startActivity(backIntent)
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding new food", e)
                }

        }
    }
}
