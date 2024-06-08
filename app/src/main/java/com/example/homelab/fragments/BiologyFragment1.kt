package com.example.homelab.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homelab.ExperimentAdapter
import com.example.homelab.R
import com.example.homelab.R.id

class BiologyFragment1 : Fragment(), ExperimentAdapter.OnItemClickListener {

    //initiating variables
    private lateinit var recyclerView: RecyclerView
    private val experiments = mutableListOf<Experiment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_biology1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initializing RecyclerView and other views
        recyclerView = view.findViewById(R.id.experimentRecycle) // Ensure the ID is correct
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // Initializing experiments list
        experiments.add(
            Experiment(
                "Plant Growth with Light",
                R.drawable.plantgrowthwithlight,
                "Investigate how light affects plant growth",
                listOf("Small pots/container","Soil", "Seeds", "Water","Light Resource"),
                listOf("Fill pots/container with soil and plant seeds.", "Place one pot in sunlight and another under artificial light", "Water the plants daily","Observe and record growth over two weeks"),
                "https://www.youtube.com/embed/jJrqmkbiwdE?si=UC_RF4MGg2KbXHj2"
            )
        )
        experiments.add(
            Experiment(
                "Dissecting a Flower",
                R.drawable.dissectingaflower,
                "Learn about the different parts of a flower by dissecting it.",
                listOf("Fresh flowers (lilies are ideal)", "Scalpel or sharp knife", "Tweezers","Magnifying glass"),
                listOf("Carefully dissect the flower with a scalpel", "Use the magnifying glass to examine parts (stamen, pistil, petals)", "Identify each part using a diagram"),
                "https://www.youtube.com/embed/T8tmVMyzu18?si=HNWtxuCf-FmkZC3u"
            )
        )

        // Set the adapter
        val adapter = ExperimentAdapter(experiments, this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(experiment: Experiment) {
        showExperimentDetails(experiment)
    }

    private fun showExperimentDetails(experiment: Experiment) {
        //initiating variables
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


        // Adding checkboxes for ingredients with loop
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

        // Configuring and loading the video in WebView
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

        //Closing the popup
        dialogView.findViewById<ImageButton>(R.id.back).setOnClickListener {
            popupWindow.dismiss()
        }
    }
}
//to use in other fragments
public final data class Experiment(
    val title: String,
    val imageResId: Int,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val videoLink: String // Add this field
)
