package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

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


        val sugarLevels = listOf(80, 90, 85, 95, 100, 92) // Sample sugar level data


        val entries = glucoseLevels.mapIndexed { index, sugarLevel ->
            Entry(index.toFloat(), sugarLevel.toFloat())
        }

        val dataSet = LineDataSet(entries, "Sugar Level")
        val lineData = LineData(dataSet)

        chart.data = lineData
        chart.invalidate() // Refresh the chart

    }
}