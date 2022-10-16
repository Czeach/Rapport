package com.czech.rapport.ui.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.czech.rapport.R

class OnboardingAdapter(private val context: Context) :
    RecyclerView.Adapter<OnboardingAdapter.ViewHolder>() {
    private val images = intArrayOf(
        R.drawable.onboarding_one,
        R.drawable.onboarding_two,
        R.drawable.onboarding_three
    )
    private val texts = arrayOf(
        "Keep your staff up to date with training \n on proper conduct at work.",
        "Offer your staff a safe and secure environment to report workplace incidents",
        "Put away the computer and utilise your phone for instant \n access to all the training you need on Rapport",
    )

    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.onboarding_placeholder, parent, false)
        return ViewHolder(view)
    }

    // This method binds the screen with the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.texts.text = texts[position].toString()
        holder.images.setImageResource(images[position])
    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return images.size
    }

    // The ViewHolder class holds the view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val images: ImageView
        val texts: TextView

        init {
            images = itemView.findViewById(R.id.onboarding_image)
            texts = itemView.findViewById(R.id.onboarding_text)
        }
    }
}