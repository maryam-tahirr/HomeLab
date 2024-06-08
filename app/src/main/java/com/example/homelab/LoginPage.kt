package com.example.homelab

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class LoginPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        //initiating the spinner variable
        val spinner: Spinner = findViewById(R.id.genderspinner)

        ArrayAdapter.createFromResource(this, R.array.genders_array, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        //selecting the gender from the spinner
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val gender = parent.getItemAtPosition(position).toString()
                Toast.makeText(this@LoginPage, "Selected: $gender", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        //initiating the variables
        val button = findViewById<Button>(R.id.createaccount)
        val inputEmail = findViewById<EditText>(R.id.inputemail)
        val inputPassword = findViewById<EditText>(R.id.Passwords)
        val inputName = findViewById<EditText>(R.id.inputname)
        val inputLastName = findViewById<EditText>(R.id.lastname)

        button.setOnClickListener {
            val name = inputName.text.toString().trim()
            val lastname = inputLastName.text.toString().trim()
            val email = inputEmail.text.toString().trim()
            val password = inputPassword.text.toString().trim()

            //making sure no field is left empty
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && lastname.isNotEmpty()) {
                val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("USER_NAME", name)
                editor.putString("USER_EMAIL",email)
                editor.apply()

                val intent = Intent(this, HomePage2::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
