package com.example.homelab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homelab.fragments.Experiment

class ExperimentAdapter(
    //initiating the variables
    private val experiments: List<Experiment>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ExperimentAdapter.ExperimentViewHolder>() {

    //making the experiment clickable
    interface OnItemClickListener {
        fun onItemClick(experiment: Experiment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperimentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.experiment, parent, false)
        return ExperimentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExperimentViewHolder, position: Int) {
        //displaying the experiments in the fragment
        val experiment = experiments[position]
        holder.titleTextView.text = experiment.title
        holder.imageView.setImageResource(experiment.imageResId)

        holder.itemView.setOnClickListener {
            listener.onItemClick(experiment)
        }
    }

    override fun getItemCount(): Int {
        return experiments.size
    }
    //displaying the experiments in the fragment
    class ExperimentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.experimentname)
        val imageView: ImageView = itemView.findViewById(R.id.experimentimage)
    }
}
