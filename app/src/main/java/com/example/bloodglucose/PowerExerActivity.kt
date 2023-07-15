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

class PowerExerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.power_exercises)

        val backButton: Button = findViewById(R.id.button_power_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@PowerExerActivity, ListExercisesActivity::class.java)
            startActivity(backIntent)
        }
        val powerEx = ArrayList<String>()
        Firebase.firestore.collection("exercises").whereEqualTo("category", "power").get()
            .addOnSuccessListener { exercises ->

                // Make a request for user-defined exercises
                Firebase.firestore.collection("users").document(USER_ID)
                    .collection("userAddedExercises").whereEqualTo("category", "power").get()
                    .addOnSuccessListener { exercises2 ->

                        for (exercise in exercises) {
                            val item = exercise.id
                            powerEx.add(item)
                        }

                        for (exercise in exercises2) {
                            val item = exercise.id
                            powerEx.add(item)
                        }

                        val listView: ListView = findViewById(R.id.listView)
                        val arrayAdapter = ArrayAdapter(
                            this, android.R.layout.simple_list_item_1, powerEx
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

                            val backIntent =
                                Intent(this@PowerExerActivity, ListExercisesActivity::class.java)
                            startActivity(backIntent)
                        }
                    }.addOnFailureListener { exception ->
                        println("Error getting documents: $exception")
                    }
            }.addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }
}
