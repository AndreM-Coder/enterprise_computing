package com.example.app_compras

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ProductAdapter (private val exampleList: List<ProductIndividual>) : RecyclerView.Adapter<ProductAdapter.ExampleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_product, parent, false)

        return ExampleViewHolder(itemView)
    }

    override fun getItemCount(): Int = exampleList.size

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]

        Picasso.get()
            .load(currentItem.imageViewProduct)
            .into(holder.imageViewProductImageIndividual)
        holder.textViewProductNameIndividual.text = currentItem.textViewProductName
        holder.textViewProductPriceIndividual.text = currentItem.textViewProductPrice
        holder.textViewProductDescriptionIndividual.text = currentItem.textviewDescription

    }

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageViewProductImageIndividual: ImageView = itemView.findViewById(R.id.imageViewProductImageIndividual)
        val textViewProductNameIndividual: TextView = itemView.findViewById(R.id.textViewProductNameIndividual)
        val textViewProductPriceIndividual: TextView = itemView.findViewById(R.id.textViewProductPriceIndividual)
        val textViewProductDescriptionIndividual: TextView = itemView.findViewById(R.id.textViewProductDescriptionIndividual)
    }

}