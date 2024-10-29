package com.example.currencyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class CurrencyAdapter(
    context: Context,
    private val currencies: List<CurrencyModel>
) : ArrayAdapter<CurrencyModel>(context, R.layout.item_currency, currencies) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_currency, parent, false)

        val currency = currencies[position]

        view.findViewById<TextView>(R.id.textViewCurrency).text = currency.name
        view.findViewById<ImageView>(R.id.imageViewFlag).setImageResource(currency.iconResId)

        return view
    }
}