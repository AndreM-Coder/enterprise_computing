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
import kotlinx.android.synthetic.main.fragment_promotions.view.*


class promotionsFragment : Fragment() {

    lateinit var textViewNoPromotionsAvailable: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_promotions, container, false)

        textViewNoPromotionsAvailable = view.findViewById(R.id.textViewNoPromotionsAvailable)

        fetchProduts {
            if(it.size == 0) {
                view.recyclerViewPromotions.adapter = PromotionsAdapter(it)
                view.recyclerViewPromotions.layoutManager = LinearLayoutManager(activity)
                textViewNoPromotionsAvailable.isVisible = true
            } else {
                view.recyclerViewPromotions.adapter = PromotionsAdapter(it)
                view.recyclerViewPromotions.layoutManager = LinearLayoutManager(activity)
                textViewNoPromotionsAvailable.isVisible = false
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
                        if (document.getString("promotion").toString() == "1"){

                            val item = Product(document.data.getValue(("id")).toString().toInt(),
                                document.data.getValue(("imageUrl")).toString(),
                                document.data.getValue(("name").toString()) as String,
                                document.getString(("price")) as String)
                            arrayProducts.add(item)
                        }
                    }
                    myCallback(arrayProducts)
                }
            }
    }






}