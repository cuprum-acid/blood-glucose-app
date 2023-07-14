package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

class TodayExercisesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.today_exercises)

        val backButton: Button = findViewById(R.id.button_today_exercises_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@TodayExercisesActivity, TodayActivity::class.java)
            startActivity(backIntent)
        }
        val db = Firebase.firestore

        db.collection("users").document(USER_ID).collection("takenExercises").get()
            .addOnSuccessListener { documents ->
                val exerciseList = ArrayList<String>()
                for (document in documents) {
                    val exerciseName = document.getString("exerciseId")
                    val medData = document.getDate("datetime").toString().substring(0,10)
                    val currentDate = Date().toString().substring(0,10)
                    if (medData == currentDate && exerciseName != null) {
                        exerciseList.add(exerciseName)
                    }
                }

                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1, // or your custom layout
                    exerciseList
                )

                val listView: ListView = findViewById(R.id.listView) // replace listView with your actual ListView id
                listView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }
}