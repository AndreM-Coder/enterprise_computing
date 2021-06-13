package com.example.app_compras

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*


class searchFragment : Fragment() {

    lateinit var textViewNoProductsAvailable: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_search, container, false)

        textViewNoProductsAvailable = view.findViewById(R.id.textViewNoProductsAvailable)

        fetchProduts1 {
            if(it.size == 0) {
                view.recyclerViewProductsFruits.adapter = SearchAdapter1(it)
                view.recyclerViewProductsFruits.layoutManager = LinearLayoutManager(activity)
                textViewNoProductsAvailable.isVisible = true
            } else {
                view.recyclerViewProductsFruits.adapter = SearchAdapter1(it)
                view.recyclerViewProductsFruits.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                textViewNoProductsAvailable.isVisible = false
            }
        }

        fetchProduts2 {
            if(it.size == 0) {
                view.recyclerViewProductsDrinks.adapter = SearchAdapter1(it)
                view.recyclerViewProductsDrinks.layoutManager = LinearLayoutManager(activity)
                textViewNoProductsAvailable.isVisible = true
            } else {
                view.recyclerViewProductsDrinks.adapter = SearchAdapter1(it)
                view.recyclerViewProductsDrinks.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                textViewNoProductsAvailable.isVisible = false
            }
        }

        fetchProduts3 {
            if(it.size == 0) {
                view.recyclerViewProductsMassas.adapter = SearchAdapter1(it)
                view.recyclerViewProductsMassas.layoutManager = LinearLayoutManager(activity)
                textViewNoProductsAvailable.isVisible = true
            } else {
                view.recyclerViewProductsMassas.adapter = SearchAdapter1(it)
                view.recyclerViewProductsMassas.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                textViewNoProductsAvailable.isVisible = false
            }
        }

        return view

    }

    private fun fetchProduts1(myCallback: (ArrayList<Product>) -> Unit) {
        val arrayProducts = ArrayList<Product>()

        FirebaseFirestore.getInstance().collection("products")
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    for(document in it.result!!) {
                        if (document.getString("category").toString() == "Frutas") {
                            val item = Product(
                                document.data.getValue(("id")).toString().toInt(),
                                document.data.getValue(("imageUrl")).toString(),
                                document.data.getValue(("name").toString()) as String,
                                document.getString(("price")) as String,
                                document.getString(("pricebefore")) as String,
                                document.getString(("category")) as String
                            )
                            arrayProducts.add(item)

                        }
                    }
                    myCallback(arrayProducts)
                }
            }
    }

    private fun fetchProduts2(myCallback: (ArrayList<Product>) -> Unit) {
        val arrayProducts = ArrayList<Product>()

        FirebaseFirestore.getInstance().collection("products")
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    for(document in it.result!!) {
                        if (document.getString("category").toString() == "Bebidas") {
                            val item = Product(
                                document.data.getValue(("id")).toString().toInt(),
                                document.data.getValue(("imageUrl")).toString(),
                                document.data.getValue(("name").toString()) as String,
                                document.getString(("price")) as String,
                                document.getString(("pricebefore")) as String,
                                document.getString(("category")) as String
                            )
                            arrayProducts.add(item)

                        }
                    }
                    myCallback(arrayProducts)
                }
            }
    }

    private fun fetchProduts3(myCallback: (ArrayList<Product>) -> Unit) {
        val arrayProducts = ArrayList<Product>()

        FirebaseFirestore.getInstance().collection("products")
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    for(document in it.result!!) {
                        if (document.getString("category").toString() == "Massas") {
                            val item = Product(
                                document.data.getValue(("id")).toString().toInt(),
                                document.data.getValue(("imageUrl")).toString(),
                                document.data.getValue(("name").toString()) as String,
                                document.getString(("price")) as String,
                                document.getString(("pricebefore")) as String,
                                document.getString(("category")) as String
                            )
                            arrayProducts.add(item)

                        }
                    }
                    myCallback(arrayProducts)
                }
            }
    }



}