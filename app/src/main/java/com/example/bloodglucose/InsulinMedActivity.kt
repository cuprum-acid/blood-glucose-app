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

class InsulinMedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insulin)

        val backButton: Button = findViewById(R.id.button_back_insulin)
        backButton.setOnClickListener {
            val backIntent = Intent(this@InsulinMedActivity, ListMedsActivity::class.java)
            startActivity(backIntent)
        }

        val insulin = ArrayList <String> ()
        Firebase.firestore.collection("medications")
            .whereEqualTo("category", "insulin").get()
            .addOnSuccessListener { medInsulin ->

                // make a request for user-defined products
                Firebase.firestore.collection("users")
                    .document(USER_ID).collection("userAddedMedications")
                    .whereEqualTo("category", "insulin").get()
                    .addOnSuccessListener { medInsulin2 ->

                        for (ins in medInsulin) {
                            val item = ins.id
                            insulin.add(item)
                        }

                        for (ins in medInsulin2) {
                            val item = ins.id
                            insulin.add(item)
                        }

                        val listView: ListView = findViewById(R.id.listView)
                        val arrayAdapter = ArrayAdapter(
                            this, android.R.layout.simple_list_item_1,
                            insulin
                        )
                        listView.adapter = arrayAdapter
                        println("Insulin size: " + insulin.size)

                        // Set the item click listener
                        listView.setOnItemClickListener { parent, view, position, id ->
                            // Get the selected item
                            val selectedItem = parent.getItemAtPosition(position) as String

                            // save the chosen item and go back to the previous screen:

                            val insulinCollection =
                                Firebase.firestore.collection("users").document(USER_ID)
                                    .collection("takenMedications")

                            // create a hashmap of values to be uploaded to the database
                            val product = hashMapOf(
                                "datetime" to FieldValue.serverTimestamp(),
                                "medicationId" to selectedItem
                            )

                            insulinCollection.add(product)

                            val backIntent =
                                Intent(this@InsulinMedActivity, ListMedsActivity::class.java)
                            startActivity(backIntent)
                        }
                    }
                    .addOnFailureListener { exception ->
                        println("Error getting documents: $exception")
                    }
            }
    }
}
