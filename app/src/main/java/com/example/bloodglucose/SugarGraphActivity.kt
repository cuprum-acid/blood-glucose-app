package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SugarGraphActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sugar_level_graph)

        val backButton: Button = findViewById(R.id.button_graph_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@SugarGraphActivity, ProfileActivity::class.java)
            startActivity(backIntent)
        }

        val exportButton: Button = findViewById(R.id.button_graph_export)

        val chart: LineChart = findViewById(R.id.chart)


        // val sugarLevels = listOf(80, 90, 85, 95, 100, 92) // Sample sugar level data

        val db = Firebase.firestore.collection("users").document(USER_ID)

        val glucoseLevels = ArrayList<Int>()

        db.collection("glucoseRecords")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val value = document.getLong("value")?.toInt()
                    if (value != null) {
                        glucoseLevels.add(value)
                    }
                    println(value)
                }

                // Move this code inside the success listener
                val entries = glucoseLevels.mapIndexed { index, sugarLevel ->
                    Entry(index.toFloat(), sugarLevel.toFloat())
                }

                val dataSet = LineDataSet(entries, "Sugar Level")
                val lineData = LineData(dataSet)

                chart.data = lineData
                chart.invalidate() // Refresh the chart
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }


    }
}
