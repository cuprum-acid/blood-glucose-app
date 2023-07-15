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

class CardioExerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cardio_exercises)

        val backButton: Button = findViewById(R.id.button_back_cardio)
        backButton.setOnClickListener {
            val backIntent = Intent(this@CardioExerActivity, ListExercisesActivity::class.java)
            startActivity(backIntent)
        }
        val cardioEx = ArrayList<String>()
        Firebase.firestore.collection("exercises").whereEqualTo("category", "cardio").get()
            .addOnSuccessListener { exercises ->

                // Make a request for user-defined exercises
                Firebase.firestore.collection("users").document(USER_ID)
                    .collection("userAddedExercises").whereEqualTo("category", "cardio").get()
                    .addOnSuccessListener { exercises2 ->

                        for (exercise in exercises) {
                            val item = exercise.id
                            cardioEx.add(item)
                        }

                        for (exercise in exercises2) {
                            val item = exercise.id
                            cardioEx.add(item)
                        }


                        val listView: ListView = findViewById(R.id.listView)
                        val arrayAdapter = ArrayAdapter(
                            this, android.R.layout.simple_list_item_1, cardioEx
                        )
                        listView.adapter = arrayAdapter


                        // Set the item click listener
                        listView.setOnItemClickListener { parent, view, position, id ->
                            // Get the selected item
                            val selectedItem = parent.getItemAtPosition(position) as String

                            // Save the chosen item and go back to the previous screen:

                            val exerciseCollection =
                                Firebase.firestore.collection("users").document(USER_ID)
                                    .collection("takenExercises")

                            // Create a hashmap of values to be uploaded to the database
                            val product = hashMapOf(
                                "datetime" to FieldValue.serverTimestamp(),
                                "exerciseId" to selectedItem
                            )

                            exerciseCollection.add(product)


                            val backIntent = Intent(
                                this@CardioExerActivity, ListExercisesActivity::class.java
                            )
                            startActivity(backIntent)
                        }
                    }.addOnFailureListener { exception ->
                        println("Error getting documents: $exception")

                    }
            }
    }
}
