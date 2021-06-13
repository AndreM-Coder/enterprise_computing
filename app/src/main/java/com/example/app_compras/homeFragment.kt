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
import kotlinx.android.synthetic.main.fragment_promotions.view.*


class homeFragment : Fragment() {


    lateinit var textViewNoProductsAvailable: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        textViewNoProductsAvailable = view.findViewById(R.id.textViewNoProductsAvailable)

        fetchProduts {
            if(it.size == 0) {
                view.recyclerViewProducts.adapter = HomeAdapter(it)
                view.recyclerViewProducts.layoutManager = LinearLayoutManager(activity)
                textViewNoProductsAvailable.isVisible = true
            } else {
                view.recyclerViewProducts.adapter = HomeAdapter(it)
                view.recyclerViewProducts.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                textViewNoProductsAvailable.isVisible = false
            }
        }

        return view

    }

    private fun fetchProduts(myCallback: (ArrayList<Product>) -> Unit) {
        val arrayProducts = ArrayList<Product>()

        FirebaseFirestore.getInstance().collection("products")
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    for(document in it.result!!) {

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
                    myCallback(arrayProducts)
                }
            }
    }


}