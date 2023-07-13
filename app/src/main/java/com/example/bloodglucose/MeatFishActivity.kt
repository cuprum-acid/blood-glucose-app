package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MeatFishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.m_and_f)

        val backButton: Button = findViewById(R.id.button_back_meat_fish_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@MeatFishActivity, ProductActivity::class.java)
            startActivity(backIntent)
        }

        // Create a list of meat and fish products
        val meatFishProducts = ArrayList<String>()

        // make a request for basic products
        Firebase.firestore.collection("foods")
            .whereEqualTo("category", "meet and fish").get()
            .addOnSuccessListener { documents ->

                // make a request for user-defined products
                Firebase.firestore.collection("users")
                    .document(USER_ID).collection("userAddedFoods")
                    .whereEqualTo("category", "meet and fish").get()
                    .addOnSuccessListener { documents2 ->

                        // save products in array
                        for (document in documents) {
                            val product = document.id
                            meatFishProducts.add(product)
                        }

                        for (document in documents2) {
                            val product = document.id
                            meatFishProducts.add(product)
                        }

                        // Find the ListView in your layout
                        val listView: ListView = findViewById(R.id.listView)

                        // Create an ArrayAdapter
                        val arrayAdapter =
                            ArrayAdapter(this, android.R.layout.simple_list_item_1, meatFishProducts)

                        // Set the ListView's adapter to the ArrayAdapter
                        listView.adapter = arrayAdapter


                        // Set the item click listener
                        listView.setOnItemClickListener { parent, view, position, id ->
                            // Get the selected item
                            val selectedItem = parent.getItemAtPosition(position) as String

                            // save the chosen item and go back to the previous screen:

                            val foodsCollection = Firebase.firestore.collection("users").document(USER_ID)
                                .collection("takenFoods")

                            // create a hashmap of values to be uploaded to the database
                            val product = hashMapOf(
                                "datetime" to FieldValue.serverTimestamp(),
                                "foodId" to selectedItem
                            )

                            foodsCollection.add(product)


                            val backIntent = Intent(this@MeatFishActivity, ProductActivity::class.java)
                            startActivity(backIntent)

                        }
                    }
                    .addOnFailureListener { exception ->
                        println("Error getting documents: $exception")
                    }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }
}
