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


class BiologyFragment3 : Fragment(), ExperimentAdapter.OnItemClickListener {
    //initiating the varibles
    private lateinit var recyclerView: RecyclerView
    private val experiments = mutableListOf<Experiment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflating the layout for this fragment
        return inflater.inflate(R.layout.fragment_biology3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initializing RecyclerView and other views
        recyclerView = view.findViewById(R.id.biorecycle3) // Ensure the ID is correct
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // Initializing your experiments list here
        experiments.add(
            Experiment(
                "Enzyme Activity",
                R.drawable.enzyme,
                "Investigate the effect of temperature on enzyme activity",
                listOf("Hydrogen peroxide", "Yeast", "Water","Beaker","Thermometer","Ice"),
                listOf("Mix yeast and water to activate the enzyme", "Divide hydrogen peroxide into several beakers at different temperatures.", "Add yeast to each beaker and measure the reaction rate."),
                "https://www.youtube.com/embed/FxjXrCyic6o?si=dNlz3FXg72O7NvcQ"
            )
        )
        experiments.add(
            Experiment(
                "Photosynthesis in Leaf Discs",
                R.drawable.photosynthesisleaf,
                "Measure the rate of photosynthesis using leaf discs",
                listOf("Leaf discs (cut with a hole punch)", "Sodium bicarbonate solution", "Syringe","Light source","Beaker"),
                listOf("Submerge leaf discs in sodium bicarbonate solution using a syringe to remove air.", "Place the discs in a beaker under a light source.", "Measure the time it takes for the discs to float as they produce oxygen."),
                "https://www.youtube.com/embed/4NM7kGKDk2A?si=QLVKMoCNe3mRoMyi"
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
