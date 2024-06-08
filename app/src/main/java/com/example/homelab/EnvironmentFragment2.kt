package com.example.homelab

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homelab.fragments.Experiment

class EnvironmentFragment2 : Fragment(), ExperimentAdapter.OnItemClickListener {
    //initiating the variables
    private lateinit var recyclerView: RecyclerView
    private val experiments = mutableListOf<Experiment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflating the layout for this fragment
        return inflater.inflate(R.layout.fragment_environment2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initializing RecyclerView and other views
        recyclerView = view.findViewById(R.id.envirrecycle2) // Ensure the ID is correct
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // Initializing your experiments list here
        experiments.add(
            Experiment(
                "Acid Rain and Its Effects on Plants",
                R.drawable.acidrain,
                "Investigate the effects of acid rain on plant growth",
                listOf("Plants", "Vinegar","Water","Spray bottle"),
                listOf("Mix water and vinegar to simulate acid rain.", "Spray plants with the mixture daily.", "Observe and record the effects on plant health over time."),
                "https://www.youtube.com/embed/M5hE6-_wSSM?si=8s7UowfCj-8GFeH0"
            )
        )
        experiments.add(
            Experiment(
                "Making a Solar Oven",
                R.drawable.solaroven,
                "Build a solar oven to understand solar energy",
                listOf("Cardboard box", "Aluminum foil", "Plastic Wrap","Black construction Paper","Tape","Thermometer"),
                listOf("Line the inside of the box with black paper.", "Cover the flap with aluminum foil.", "Seal the opening with plastic wrap.","Place the thermometer inside and observe temperature changes."),
                "https://www.youtube.com/embed/HFwXPkI5OtU?si=8GVpHFx_p55CdUAg"
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

        //Closing the popup
        dialogView.findViewById<ImageButton>(R.id.back).setOnClickListener {
            popupWindow.dismiss()
        }
    }
}
