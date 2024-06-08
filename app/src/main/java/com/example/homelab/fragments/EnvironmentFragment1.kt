package com.example.homelab.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homelab.ExperimentAdapter
import com.example.homelab.R

class EnvironmentFragment1 : Fragment(), ExperimentAdapter.OnItemClickListener {
    //intiating the variables
    private lateinit var recyclerView: RecyclerView
    private val experiments = mutableListOf<Experiment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_environment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initializing RecyclerView and other views
        recyclerView = view.findViewById(R.id.envrecycle1) // Ensure the ID is correct
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // Initializing your experiments list here
        experiments.add(
            Experiment(
                "Water Filtration",
                R.drawable.waterfiltration,
                "Build a simple water filter to understand filtration",
                listOf("Plastic Bottle", "Sand", "Gravel","Activated Charcoal","Cotton balls","Dirty Water"),
                listOf("Cut the bottom off the plastic bottle", "Layer cotton balls, charcoal, gravel, and sand inside the bottle", "Pour dirty water through the filter and observe the clean water"),
                "https://www.youtube.com/embed/60Big9Ut6Mc?si=V7LShRryaH_xIfU-"
            )
        )
        experiments.add(
            Experiment(
                "Homemade Weather Station",
                R.drawable.homemadeweatherstation,
                "Create simple tools to measure weather conditions",
                listOf("Thermometer", "Plastic bottle", "Ruler","Water","Cardboard"),
                listOf("Construct a rain gauge using a plastic bottle and ruler", "Create a wind vane using cardboard", "Record daily weather conditions using the homemade tools"),
                "https://www.youtube.com/embed/WO7f1mXuWe4?si=1cQ4XmzbZYUGO2ay"
            )
        )

        // Setting the adapter
        val adapter = ExperimentAdapter(experiments, this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(experiment: Experiment) {
        showExperimentDetails(experiment)
    }

    private fun showExperimentDetails(experiment: Experiment) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.activity_experiment_details_popup, null)
        val popupTitle: TextView = dialogView.findViewById(R.id.expName)
        val experimentDetail: TextView = dialogView.findViewById(R.id.expDecription)
        val ingredientsContainer: LinearLayout = dialogView.findViewById(R.id.ingredientsContainer)
        val stepsContainer: LinearLayout = dialogView.findViewById(R.id.stepsContainer)
        val videoWebView: WebView = dialogView.findViewById(R.id.videoWebView)

        // Setting experiment details
        popupTitle.text = experiment.title
        experimentDetail.text = experiment.description

        val expImage: ImageView = dialogView.findViewById(R.id.expImage)
        expImage.setImageResource(experiment.imageResId)


        // Adding checkboxes for ingredients
        ingredientsContainer.removeAllViews()
        for (ingredient in experiment.ingredients) {
            val checkBox = CheckBox(requireContext()).apply {
                text = ingredient
            }
            ingredientsContainer.addView(checkBox)
        }

        // Adding TextViews for steps
        stepsContainer.removeAllViews()
        for ((index, step) in experiment.steps.withIndex()) {
            val stepTextView = TextView(requireContext()).apply {
                text = "Step ${index + 1}: $step"
            }
            stepsContainer.addView(stepTextView)
        }

        // Configuring and load the video in WebView
        videoWebView.settings.apply {
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            cacheMode = WebSettings.LOAD_NO_CACHE // Disable caching
            domStorageEnabled = true
        }
        videoWebView.webViewClient = WebViewClient()
        videoWebView.loadUrl(experiment.videoLink)

        // Creating the PopupWindow
        val popupWindow = PopupWindow(dialogView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.isFocusable = true

        // Showing the PopupWindow
        popupWindow.showAtLocation(view, android.view.Gravity.CENTER, 0, 0)

        //Closing the popup when the OK button is clicked
        dialogView.findViewById<ImageButton>(R.id.back).setOnClickListener {
            popupWindow.dismiss()
        }
    }
}
