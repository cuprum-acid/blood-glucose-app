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

class StaticExerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.static_exercises)

        // find back button and set its click listener
        val backButton: Button = findViewById(R.id.button_back_static)
        backButton.setOnClickListener {
            // on click, start ListExercisesActivity
            val backIntent = Intent(this@StaticExerActivity, ListExercisesActivity::class.java)
            startActivity(backIntent)
        }

        // list to hold exercise IDs
        val staticEx = ArrayList<String>()

        // request for exercises of category "static" from Firebase Firestore
        Firebase.firestore.collection("exercises").whereEqualTo("category", "static").get()
            .addOnSuccessListener { exercises ->

                // request for user-defined exercises of category "static"
                Firebase.firestore.collection("users")
                    .document(USER_ID).collection("userAddedExercises")
                    .whereEqualTo("category", "static").get()
                    .addOnSuccessListener { exercises2 ->

                        // add exercise ids from exercises to staticEx list
                        for (exercise in exercises) {
                            val item = exercise.id
                            staticEx.add(item)
                        }

                        // add exercise ids from user-defined exercises to staticEx list
                        for (exercise in exercises2) {
                            val item = exercise.id
                            staticEx.add(item)
                        }

                        // find listView and set its adapter
                        val listView: ListView = findViewById(R.id.listView)
                        val arrayAdapter = ArrayAdapter(
                            this, android.R.layout.simple_list_item_1,
                            staticEx
                        )
                        listView.adapter = arrayAdapter

                        // set click listener for items in listView
                        listView.setOnItemClickListener { parent, view, position, id ->
                            // get selected item
                            val selectedItem = parent.getItemAtPosition(position) as String

                            // add selected exercise to "takenExercises" collection in Firestore
                            val exerciseCollection =
                                Firebase.firestore.collection("users").document(USER_ID)
                                    .collection("takenExercises")

                            // create a hashmap of values to be uploaded to the database
                            val product = hashMapOf(
                                "datetime" to FieldValue.serverTimestamp(),
                                "exerciseId" to selectedItem
                            )

                            exerciseCollection.add(product)

                            // start ListExercisesActivity
                            val backIntent =
                                Intent(this@StaticExerActivity, ListExercisesActivity::class.java)
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
