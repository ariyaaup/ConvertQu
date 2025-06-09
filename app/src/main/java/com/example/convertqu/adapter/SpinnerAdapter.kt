package com.example.convertqu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.convertqu.R

class SpinnerAdapter(
    context: Context,
    private val flags: List<Int>, // List of drawable resource IDs for flags
    private val countries: List<String> // List of country names
) : ArrayAdapter<String>(context, 0, countries) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.spiner_item, parent, false)
        val flagImageView = view.findViewById<ImageView>(R.id.flagImage)
        val countryTextView = view.findViewById<TextView>(R.id.countryName)

        flagImageView.setImageResource(flags[position])
        countryTextView.text = countries[position]

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}
