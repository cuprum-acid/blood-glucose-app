package com.example.bloodglucose

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random
import java.text.SimpleDateFormat
import java.util.*

class AdviceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advice)

        val homeButton: Button = findViewById(R.id.button_home_advice)
        homeButton.setOnClickListener {
            val homeIntent = Intent(this@AdviceActivity, TodayActivity::class.java)
            startActivity(homeIntent)
        }
        val imageView: ImageView = findViewById(R.id.imageView)
        val textView: TextView = findViewById(R.id.textView)

        val sharedPreferences = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        val savedDate = sharedPreferences.getString("date", "")
        val savedTip = sharedPreferences.getString("tip", "")
        val savedImageUrl = sharedPreferences.getString("imageUrl", "")

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val currentDate = dateFormat.format(Date())

        if (savedDate == currentDate && savedTip != null && savedImageUrl != null) {
            // Load the saved tip and image
            textView.text = savedTip
            Glide.with(this@AdviceActivity).load(savedImageUrl).into(imageView)
        } else {
            // Fetch a new random tip and image
            Firebase.firestore.collection("tips").get().addOnSuccessListener { advice ->
                val randomIndex = Random.nextInt(0, advice.size() - 1)
                val item = advice.documents[randomIndex]

                val url = item.getString("image")
                val tipText = item.getString("text")

                Glide.with(this@AdviceActivity).load(url).into(imageView)
                textView.text = tipText

                // Save the new tip and image URL
                with(sharedPreferences.edit()) {
                    putString("date", currentDate)
                    putString("text", tipText)
                    putString("image", url)
                    apply()
                }
            }.addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
        }
    }



}