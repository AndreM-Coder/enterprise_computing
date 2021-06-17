package com.example.app_compras

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_cart.view.*

class cartFragment : Fragment() {

    lateinit var textViewNoProductsInCartAvailable: TextView
    private val mAuth = FirebaseAuth.getInstance()
    lateinit var buttonClearCart: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        textViewNoProductsInCartAvailable = view.findViewById(R.id.textViewNoProductsInCartAvailable)
        buttonClearCart = view.findViewById(R.id.buttonClearCart)

        loadProductsCart {
            if(it.size == 0) {
                view.recyclerview_cart.adapter = CartAdapter(it)
                view.recyclerview_cart.layoutManager = LinearLayoutManager(activity)
                textViewNoProductsInCartAvailable.isVisible = true
            } else {
                view.recyclerview_cart.adapter = CartAdapter(it)
                view.recyclerview_cart.layoutManager = LinearLayoutManager(activity)
                textViewNoProductsInCartAvailable.isVisible = false
            }
        }

        buttonClearCart.setOnClickListener {
            clearCart()
        }

        return view
    }

    private fun loadProductsCart(myCallback: (ArrayList<Product>) -> Unit) {
        val arrayCart = ArrayList<Product>()

        val uid = mAuth.uid.toString()

        FirebaseFirestore.getInstance().collection(uid)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    for(document in it.result!!) {
                        val item = Product(document.data.getValue(("id")).toString().toInt(),
                            document.data.getValue(("imageUrl")).toString(),
                            document.data.getValue(("name").toString()) as String,
                            document.getString(("price")) as String,
                            document.getString(("pricebefore")) as String,
                            document.getString(("category")) as String,
                            document.getString(("description")) as String,
                            document.data.getValue(("quantidade")).toString().toInt(),
                            document.getString(("stock")) as String,
                            document.getString(("promotion")) as String)
                        arrayCart.add(item)
                    }
                    myCallback(arrayCart)
                }
            }
    }

    private fun clearCart() {

        val uid = mAuth.uid.toString()

        FirebaseFirestore.getInstance().collection(uid)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        FirebaseFirestore.getInstance().collection(uid)
                            .document(document.data.getValue("name").toString())
                            .delete()
                    }
                }
            }
    }
}