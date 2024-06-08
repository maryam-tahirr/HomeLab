package com.example.homelab

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog


class HomePage2 : AppCompatActivity() {

    //initiatinf the variables
    private lateinit var showpopupinstruction: ImageButton
    private lateinit var showdialog : ImageButton
    private lateinit var bubbles: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page2)

        bubbles = findViewById(R.id.imageView2)
        val animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        bubbles.startAnimation(animation)

        //configuring the shared preferences
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Check if "MUSIC_ENABLED" preference exists, if not set it to true by default
        if (!sharedPreferences.contains("MUSIC_ENABLED")) {
            editor.putBoolean("MUSIC_ENABLED", true)
            editor.apply()
        }

        //displaying the name using shared preferences
        val name = sharedPreferences.getString("USER_NAME", "Guest")
        val musicEnabled = sharedPreferences.getBoolean("MUSIC_ENABLED", true)

        if (musicEnabled) {
            startService(Intent(this, MusicService::class.java))
        }


        // Setting the retrieved name to a TextView
        val textViewName = findViewById<TextView>(R.id.name)
        textViewName.text = "Hello, $name"

        findViewById<ImageButton>(R.id.age1).setOnClickListener {
            val intent = Intent(this, Age1::class.java)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.age2).setOnClickListener {
            val intent = Intent(this, Age2::class.java)
            startActivity(intent)
        }
        findViewById<ImageButton>(R.id.age3).setOnClickListener {
            val intent = Intent(this, Age3::class.java)
            startActivity(intent)
        }

        //opening the setting and instructions
        showpopupinstruction = findViewById(R.id.settings)
        showpopupinstruction.setOnClickListener {
            showPopup()
        }

        showdialog = findViewById(R.id.ins)
        showdialog.setOnClickListener {
           InstructionsPopup()
        }
    }
    //Instructions popup
    private fun InstructionsPopup() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupVieww = inflater.inflate(R.layout.instructions_popup, null)

        val width = 1000
        val height = 900

        val instructionss = PopupWindow(popupVieww, width, height, true)
        instructionss.showAtLocation(popupVieww, Gravity.CENTER, 0,0)

        //closing the popup with animation
        val closeebutton = popupVieww.findViewById<Button>(R.id.closeInstructionsButton)
        closeebutton.setOnClickListener {
            val animati = AnimationUtils.loadAnimation(this@HomePage2, R.anim.slide_outright)
            animati.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    //dismissing the window
                    instructionss.dismiss()
                }
                override fun onAnimationRepeat(animation: Animation) {}
            })
            popupVieww.startAnimation(animati)
        }

        popupVieww.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_inright))
    }

    //Settings popup
    private fun showPopup() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup, null)
        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_inright)
        popupView.startAnimation(animation)

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT

        val instructwindow = PopupWindow(popupView, width, height, true)
        instructwindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

        // Ensuring to get the SharedPreferences correctly
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val name = sharedPreferences.getString("USER_NAME", "Guest")
        val email = sharedPreferences.getString("USER_EMAIL", "Guest")

        // Finding views within popupView
        val nameTextView = popupView.findViewById<TextView>(R.id.nameee)
        val emailTextView = popupView.findViewById<TextView>(R.id.emailll)

        // Checking if the views are found
        if (nameTextView != null && emailTextView != null) {
            nameTextView.text = name
            emailTextView.text = email
        } else {
            // Handle the error case where the views are not found
            return
        }
        val logout = popupView.findViewById<Button>(R.id.logout)
        logout.setOnClickListener{
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }

        //closing the popup with animation
        val closebutton = popupView.findViewById<ImageButton>(R.id.closebutton)
        closebutton.setOnClickListener {
            val animati = AnimationUtils.loadAnimation(this@HomePage2, R.anim.slide_outright)
            animati.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    instructwindow.dismiss()
                }
                override fun onAnimationRepeat(animation: Animation) {}
            })
            popupView.startAnimation(animati)
        }

        //For the Music switch which opens and closes
        val musicSwitch = popupView.findViewById<Switch>(R.id.musicSwitch)

        val musicEnabled = sharedPreferences.getBoolean("MUSIC_ENABLED", true)
        musicSwitch.isChecked = musicEnabled
        //checking if the switch is open or close
        musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                startService(Intent(this, MusicService::class.java))
                editor.putBoolean("MUSIC_ENABLED", true)
            } else {
                stopService(Intent(this, MusicService::class.java))
                editor.putBoolean("MUSIC_ENABLED", false)
            }
            editor.apply()
        }
        //For the seekbar to adjust the volume of the music
        val volumeSeekBar = popupView.findViewById<SeekBar>(R.id.volumeSeekBar)
        volumeSeekBar.progress = sharedPreferences.getInt("MUSIC_VOLUME", 50)
        volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val volume = progress / 100f
                Intent(this@HomePage2, MusicService::class.java).also { intent ->
                    intent.putExtra("VOLUME", volume)
                    startService(intent)
                }
                editor.putInt("MUSIC_VOLUME", progress)
                editor.apply()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

    }
}
