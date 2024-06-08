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

class ChemistryFragment1 : Fragment(), ExperimentAdapter.OnItemClickListener {
    //initiating variables
    private lateinit var recyclerView: RecyclerView
    private val experiments = mutableListOf<Experiment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chemistry1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initializing RecyclerView and other views
        recyclerView = view.findViewById(R.id.recycle) // Ensure the ID is correct
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // Initializing your experiments list here
        experiments.add(
            Experiment(
                "Baking Soda and Vinegar Volcano",
                R.drawable.bakingsodaandvinegarvolcano,
                "Create a mini volcano eruption using baking soda and vinegar",
                listOf("Baking Soda", "Vinegar", "Dish Soap","Food Coloring","Plastic Bottle","Clay or Play dough"),
                listOf("Build a volcano around the bottle using clay", "Add baking soda, dish soap and food coloring inside the bottle", "Pour vinegar into the bottle and watch the eruption."),
                "https://www.youtube.com/embed/XTynyUXPKEo?si=yZe53crfo_ENW0gD"
            )
        )
        experiments.add(
            Experiment(
                "Making Slime",
                R.drawable.makingslime,
                "Learn about polymer chains by making slime",
                listOf("White Glue", "Baking Soda", "Contact lens solution","Food coloring(optional)"),
                listOf("Mix glue and baking soda in a bowl", "Add food coloring if desired", "Slowly add contact lens solution while stirring until slime forms"),
                "https://www.youtube.com/embed/mTf9LhQrlqc?si=wcNttYHNJw4oTqdW"
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
