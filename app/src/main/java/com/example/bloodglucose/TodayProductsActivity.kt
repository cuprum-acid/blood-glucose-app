package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.mytestapp.Tuple6
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

class TodayProductsActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var listView: ListView
    private lateinit var protView: TextView
    private lateinit var fatView: TextView
    private lateinit var carbView: TextView
    private lateinit var calView: TextView
    private lateinit var buView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.today_products)

        backButton = findViewById(R.id.button_today_products_back)
        listView = findViewById(R.id.listView)
        protView = findViewById(R.id.textProt)
        fatView = findViewById(R.id.fatView)
        carbView = findViewById(R.id.carView)
        calView = findViewById(R.id.calView)
        buView = findViewById(R.id.buView)

        backButton.setOnClickListener {
            val backIntent = Intent(this@TodayProductsActivity, TodayActivity::class.java)
            startActivity(backIntent)
        }

        val db = Firebase.firestore
        val foodList = ArrayList<String>()
        val allFoods = ArrayList<Tuple6<String, Double, Double, Double, Double, Double>>()

        db.collection("users").document(USER_ID).collection("takenFoods").get()
            .addOnSuccessListener { documents ->
                var pending = documents.size()
                for (document in documents) {
                    val foodName = document.getString("foodId")
                    val medData = document.getDate("datetime").toString().substring(0, 10)
                    val currentDate = Date().toString().substring(0, 10)

                    if (medData == currentDate && foodName != null) {
                        foodList.add(foodName)

                        db.collection("foods").document(foodName).get()
                            .addOnSuccessListener { documentFood ->
                                val foodProteins = Tuple6(
                                    foodName,
                                    documentFood.getDouble("proteins") ?: 0.0,
                                    documentFood.getDouble("fats") ?: 0.0,
                                    documentFood.getDouble("carbohydrates") ?: 0.0,
                                    documentFood.getDouble("calories") ?: 0.0,
                                    (documentFood.getDouble("carbohydrates") ?: 0.0) / 11,
                                )

                                allFoods.add(foodProteins)
                                pending--
                                checkPending(pending, foodList, allFoods)
                            }.addOnFailureListener { exception ->
                                println("Error getting documents: $exception")
                                pending--
                                checkPending(pending, foodList, allFoods)
                            }
                    } else {
                        pending--
                        checkPending(pending, foodList, allFoods)
                    }
                }
            }.addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }

    private fun checkPending(
        pending: Int,
        foodList: ArrayList<String>,
        allFoods: ArrayList<Tuple6<String, Double, Double, Double, Double, Double>>
    ) {
        if (pending == 0) { // All requests have returned a result
            updateUI(foodList, allFoods)
        }
    }

    private fun updateUI(
        foodList: ArrayList<String>,
        allFoods: ArrayList<Tuple6<String, Double, Double, Double, Double, Double>>
    ) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, foodList)
        listView.adapter = adapter

        var protFood = 0.0
        var fatFood = 0.0
        var calFood = 0.0
        var carFood = 0.0
        var buFood = 0.0
        for (pr in allFoods) {
            protFood += pr.second
            fatFood += pr.third
            carFood += pr.fourth
            calFood += pr.fifth
            buFood += pr.sixth
        }

        protView.text = protFood.toInt().toString()
        fatView.text = fatFood.toInt().toString()
        carbView.text = carFood.toInt().toString()
        buView.text = buFood.toInt().toString()
        calView.text = calFood.toInt().toString()
    }
}