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

class PillsMedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pills)

        val backButton: Button = findViewById(R.id.button_pills_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@PillsMedActivity, ListMedsActivity::class.java)
            startActivity(backIntent)
        }
        val pills = ArrayList<String>()
        Firebase.firestore.collection("medications").whereEqualTo("category", "pills").get()
            .addOnSuccessListener { medPills ->


                // Make a request for user-defined products
                Firebase.firestore.collection("users").document(USER_ID)
                    .collection("userAddedMedications").whereEqualTo("category", "pills").get()
                    .addOnSuccessListener { medPills2 ->

                        for (pill in medPills) {
                            val item = pill.id
                            pills.add(item)
                        }

                        for (pill in medPills2) {
                            val item = pill.id
                            pills.add(item)
                        }

                        val listView: ListView = findViewById(R.id.listView)
                        val arrayAdapter = ArrayAdapter(
                            this, android.R.layout.simple_list_item_1, pills
                        )
                        listView.adapter = arrayAdapter
                        println("Pills size: " + pills.size)


                        // Set the item click listener
                        listView.setOnItemClickListener { parent, view, position, id ->
                            // Get the selected item
                            val selectedItem = parent.getItemAtPosition(position) as String

                            // Save the chosen item and go back to the previous screen:

                            val pillsCollection =
                                Firebase.firestore.collection("users").document(USER_ID)
                                    .collection("takenMedications")

                            // Create a hashmap of values to be uploaded to the database
                            val product = hashMapOf(
                                "datetime" to FieldValue.serverTimestamp(),
                                "medicationId" to selectedItem
                            )

                            pillsCollection.add(product)


                            val backIntent =
                                Intent(this@PillsMedActivity, ListMedsActivity::class.java)
                            startActivity(backIntent)
                        }
                    }.addOnFailureListener { exception ->
                        println("Error getting documents: $exception")

                    }
            }
    }

}
