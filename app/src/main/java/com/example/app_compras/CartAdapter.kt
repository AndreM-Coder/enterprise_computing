package com.example.app_compras

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class CartAdapter(private val exampleList: ArrayList<Product>) : RecyclerView.Adapter<CartAdapter.ExampleViewHolder>(){

    private val mAuth = FirebaseAuth.getInstance()
    private val authFireStore = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row_cart, parent, false)

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
        holder.quantidade.text = currentItem.quantidade.toString()

        holder.buttonAddQuantity.setOnClickListener {

            val uid = mAuth.uid.toString()

            FirebaseFirestore.getInstance().collection(uid).document(currentItem.textViewProductName)
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        val quantidadeAtual = it.result!!.data?.get("quantidade").toString().toInt()
                        val carrinho = HashMap<String, Any>()
                        carrinho["category"] = currentItem.categoryProduct
                        carrinho["description"] = currentItem.descriptionProduct
                        carrinho["id"] = currentItem.id
                        carrinho["imageUrl"] = currentItem.imageViewProduct
                        carrinho["name"] = currentItem.textViewProductName
                        carrinho["price"] = currentItem.textViewProductPrice
                        carrinho["pricebefore"] = currentItem.textViewProductPriceBefore
                        carrinho["promotion"] = currentItem.promotion
                        carrinho["quantidade"] = quantidadeAtual + 1
                        carrinho["stock"] = currentItem.stock

                        authFireStore.collection(uid).document(currentItem.textViewProductName).set(carrinho).addOnCompleteListener { it1 ->
                            when {
                                it1.isSuccessful -> {
                                    Log.d("aaaa", "foi adicionado + 1 quantidade deste produto")
                                }
                                else -> {
                                    Log.d("aaaa", "não foi adicionado + 1 quantidade deste produto")
                                }
                            }
                        }
                    }
                }
        }

        holder.buttonSubtractQuantity.setOnClickListener {
            val uid = mAuth.uid.toString()

            FirebaseFirestore.getInstance().collection(uid).document(currentItem.textViewProductName)
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        val quantidadeAtual = it.result!!.data?.get("quantidade").toString().toInt()
                        if(quantidadeAtual == 1) {
                            FirebaseFirestore.getInstance().collection(uid)
                                .get()
                                .addOnCompleteListener { it3->
                                    if (it3.isSuccessful) {
                                        for (document in it3.result!!) {
                                            FirebaseFirestore.getInstance().collection(uid).document(currentItem.textViewProductName)
                                                .delete()
                                        }
                                    }
                                }
                        } else {
                            val carrinho = HashMap<String, Any>()
                            carrinho["category"] = currentItem.categoryProduct
                            carrinho["description"] = currentItem.descriptionProduct
                            carrinho["id"] = currentItem.id
                            carrinho["imageUrl"] = currentItem.imageViewProduct
                            carrinho["name"] = currentItem.textViewProductName
                            carrinho["price"] = currentItem.textViewProductPrice
                            carrinho["pricebefore"] = currentItem.textViewProductPriceBefore
                            carrinho["promotion"] = currentItem.promotion
                            carrinho["quantidade"] = quantidadeAtual - 1
                            carrinho["stock"] = currentItem.stock

                            authFireStore.collection(uid).document(currentItem.textViewProductName).set(carrinho).addOnCompleteListener { it1 ->
                                when {
                                    it1.isSuccessful -> {
                                        Log.d("aaaa", "foi adicionado + 1 quantidade deste produto")
                                    }
                                    else -> {
                                        Log.d("aaaa", "não foi adicionado + 1 quantidade deste produto")
                                    }
                                }
                            }
                        }
                    }
                }
        }

    }

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageViewProduct: ImageView = itemView.findViewById(R.id.imageViewProduct)
        val textViewProductName: TextView = itemView.findViewById(R.id.textViewProductName)
        val textViewProductPrice: TextView = itemView.findViewById(R.id.textViewProductPrice)
        val quantidade: TextView = itemView.findViewById(R.id.textViewQuantityProductCart)
        val buttonAddQuantity: TextView = itemView.findViewById(R.id.buttonAddQuantity)
        val buttonSubtractQuantity: TextView = itemView.findViewById(R.id.buttonSubtractQuantity)
    }


}