package com.example.bloodglucose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_list)

        val backButton: Button = findViewById(R.id.button_back_product_list)
        backButton.setOnClickListener {
            val backIntent = Intent(this@ProductActivity, ProfileActivity::class.java)
            startActivity(backIntent)
        }

        val dairyButton: Button = findViewById(R.id.button_dairy_list_pr)
        dairyButton.setOnClickListener {
            val dairyIntent = Intent(this@ProductActivity, DairyActivity::class.java)
            startActivity(dairyIntent)
        }

        val mfButton: Button = findViewById(R.id.button_m_and_f_list_pr)
        mfButton.setOnClickListener {
            val mfIntent = Intent(this@ProductActivity, MeatFishActivity::class.java)
            startActivity(mfIntent)
        }

        val vfButton: Button = findViewById(R.id.button_v_and_f_list_pr)
        vfButton.setOnClickListener {
            val vfIntent = Intent(this@ProductActivity, VegsFruitsActivity::class.java)
            startActivity(vfIntent)
        }

        val groceryButton: Button = findViewById(R.id.button_grocery_list_pr)
        groceryButton.setOnClickListener {
            val groceryIntent = Intent(this@ProductActivity, GroceryActivity::class.java)
            startActivity(groceryIntent)
        }

        val psButton: Button = findViewById(R.id.button_p_and_s_list_pr)
        psButton.setOnClickListener {
            val psIntent = Intent(this@ProductActivity, PastrSweetsActivity::class.java)
            startActivity(psIntent)
        }

        val plusButton: Button = findViewById(R.id.button_plus_product_list)
        plusButton.setOnClickListener {
            val plusIntent = Intent(this@ProductActivity, AddProductActivity::class.java)
            startActivity(plusIntent)
        }
        val nextButton: Button = findViewById(R.id.button_next_product_list)
        nextButton.setOnClickListener {
            val nextIntent = Intent(this@ProductActivity, ListMedsActivity::class.java)
            startActivity(nextIntent)
        }
    }
}