package com.example.homelab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //navigating to the next page
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
    }
}