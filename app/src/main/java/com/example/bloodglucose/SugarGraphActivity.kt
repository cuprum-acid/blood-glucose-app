package com.example.bloodglucose

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Calendar
import java.util.Date
import java.util.Locale


data class DataPoint(val datetime: Date, val value: Int) {
    val dateOnly: Date
        get() {
            val cal = Calendar.getInstance()
            cal.time = datetime
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            return cal.time
        }
}


class SugarGraphActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sugar_level_graph)

        val backButton: Button = findViewById(R.id.button_graph_back)
        backButton.setOnClickListener {
            val backIntent = Intent(this@SugarGraphActivity, ProfileActivity::class.java)
            startActivity(backIntent)
        }

        val chart: LineChart = findViewById(R.id.chart)
        val db = Firebase.firestore.collection("users").document(USER_ID)

        val glucoseDataPoints = ArrayList<DataPoint>()

        db.collection("glucoseRecords")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val value = document.getLong("value")?.toInt()
                    val datetime = document.getDate("datetime")
                    if (value != null && datetime != null) {
                        glucoseDataPoints.add(DataPoint(datetime, value))
                    }
                    println(value)
                }

                val grouped = glucoseDataPoints.groupBy { it.dateOnly }
                var averageDataPoints = grouped.map { (date, dataPoints) ->
                    val average = dataPoints.map { it.value }.average()
                    DataPoint(date, average.toInt())
                }

                averageDataPoints = averageDataPoints.sortedBy { it.datetime }

                val entries = averageDataPoints.mapIndexed { index, dataPoint ->
                    Entry(index.toFloat(), dataPoint.value.toFloat())
                }

                val dataSet = LineDataSet(entries, "Average Sugar Level")
                val lineData = LineData(dataSet)

                chart.data = lineData

                val formatter = object : ValueFormatter() {
                    private val dateFormat = SimpleDateFormat("MM-dd", Locale.US) // Customize this format to your needs

                    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                        val index = value.toInt()
                        return if (index < averageDataPoints.size) {
                            dateFormat.format(averageDataPoints[index].datetime)
                        } else {
                            "" // Return an empty string for out-of-bounds index
                        }
                    }
                }

                chart.xAxis.valueFormatter = formatter
                chart.invalidate() // Refresh the chart
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }

}
