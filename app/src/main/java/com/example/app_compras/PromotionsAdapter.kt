package com.example.app_compras

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class PromotionsAdapter (private val exampleList: List<Product>) : RecyclerView.Adapter<PromotionsAdapter.ExampleViewHolder>() {

    private lateinit var navController: NavController
    private val mAuth = FirebaseAuth.getInstance()
    private val authFireStore = FirebaseFirestore.getInstance().collection("Carrinho")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row, parent, false)

        return ExampleViewHolder(itemView)

    }

    override fun getItemCount(): Int = exampleList.size

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]

        Picasso.get()
            .load(currentItem.imageViewProduct)
            .into(holder.imageViewProduct)
        holder.textViewProductName.text = currentItem.textViewProductName
        holder.textViewProductPrice.text = currentItem.textViewProductPrice
        holder.textViewProductPriceBefore.text = currentItem.textViewProductPriceBefore
        holder.textViewProductPriceBefore.paintFlags = holder.textViewProductPriceBefore.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        holder.itemView.setOnClickListener { view ->
            val bundle = Bundle().apply {
                putString("produtoId", currentItem.id.toString())
            }

            val fragment = ProductFragment()
            val fragment2 = promotionsFragment()
            fragment.arguments = bundle
            fragment2.arguments = bundle

            val manager: FragmentManager = (view.context as AppCompatActivity).supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.fl_wrapper, fragment)
                .addToBackStack(null)
                .commit()
        }

        holder.buttonCart.setOnClickListener {
            Log.d("aaaa", "carregaste no carrinho")
        }
    }

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageViewProduct: ImageView = itemView.findViewById(R.id.imageViewProduct)
        val textViewProductName: TextView = itemView.findViewById(R.id.textViewProductName)
        val textViewProductPrice: TextView = itemView.findViewById(R.id.textViewProductPrice)
        val textViewProductPriceBefore: TextView = itemView.findViewById(R.id.textViewProductPriceBefore)
        val buttonCart: Button = itemView.findViewById(R.id.buttonCart)
    }

    private fun addCart(produtoId: String) {
        val produto = HashMap<String, Any>()
        produto["id"] = produtoId
        val uid = mAuth.uid.toString()
        authFireStore.document(uid).set(produto).addOnCompleteListener { it1 ->
            when {
                it1.isSuccessful -> {
                    Log.d("aaaa", "adicionado")
                }
                else -> {
                    Log.d("aaaa", "não adicionou")
                }
            }
        }
    }

}