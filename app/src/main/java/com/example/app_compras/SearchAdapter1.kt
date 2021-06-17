package com.example.app_compras

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class SearchAdapter1 (private val exampleList: List<Product>) : RecyclerView.Adapter<SearchAdapter1.ExampleViewHolder>() {

    private lateinit var navController: NavController

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row_categories_search, parent, false)


        return ExampleViewHolder(itemView)

    }

    override fun getItemCount(): Int = exampleList.size

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]

        Picasso.get()
            .load(currentItem.imageViewProduct)
            .into(holder.imageViewProduct)
        holder.textViewProductName.text = currentItem.textViewProductName
        holder.textViewProductPrice.text = (currentItem.textViewProductPrice + "€")

        holder.itemView.setOnClickListener { view ->
            val bundle = Bundle().apply {
                putString("produtoId", currentItem.id.toString())
            }

            val fragment = ProductFragment()
            fragment.arguments = bundle

            val manager: FragmentManager =
                (view.context as AppCompatActivity).supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.fl_wrapper, fragment)
                .addToBackStack(null)
                .commit()

        }

    }
    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageViewProduct: ImageView = itemView.findViewById(R.id.imageViewProduct)
        val textViewProductName: TextView = itemView.findViewById(R.id.textViewProductName)
        val textViewProductPrice: TextView = itemView.findViewById(R.id.textViewProductPrice)
    }

}