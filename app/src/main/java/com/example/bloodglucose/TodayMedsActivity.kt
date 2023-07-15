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

class TodayMedsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.today_meds)

        val backButton: Button = findViewById(R.id.button_today_meds_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@TodayMedsActivity, TodayActivity::class.java)
            startActivity(backIntent)
        }
        val db = Firebase.firestore

        db.collection("users").document(USER_ID).collection("takenMedications").get()
            .addOnSuccessListener { documents ->
                val medicationList = ArrayList<String>()
                for (document in documents) {
                    val medicationName = document.getString("medicationId")
                    val medData = document.getDate("datetime").toString().substring(0, 10)
                    val currentDate = Date().toString().substring(0, 10)
                    if (medData == currentDate && medicationName != null) {
                        medicationList.add(medicationName)
                    }
                }

                val adapter = ArrayAdapter(
                    this, android.R.layout.simple_list_item_1, // Or your custom layout
                    medicationList
                )

                val listView: ListView =
                    findViewById(R.id.listView) // Replace listView with your actual ListView id
                listView.adapter = adapter
            }.addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }
}