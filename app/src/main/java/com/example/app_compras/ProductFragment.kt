package com.example.app_compras

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_promotions.view.*

class ProductFragment : Fragment() {

    lateinit var textViewProductNameIndividual: TextView
    lateinit var textViewProductPriceIndividual: TextView
    lateinit var imageViewProductImageIndividual: ImageView
    lateinit var textViewProductDescriptionIndividual: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_product, container, false)

        textViewProductNameIndividual = view.findViewById(R.id.textViewProductNameIndividual)
        textViewProductPriceIndividual = view.findViewById(R.id.textViewProductPriceIndividual)
        imageViewProductImageIndividual = view.findViewById(R.id.imageViewProductImageIndividual)
        textViewProductDescriptionIndividual = view.findViewById(R.id.textViewProductDescriptionIndividual)

        val bundle = this.arguments
        if (bundle != null) {
            val produtoId = bundle.get("produtoId").toString()

            carregarProduto(produtoId)
        }

        return view

    }

    private fun carregarProduto(produtoId: String) {

        FirebaseFirestore.getInstance().collection("products").document(produtoId)
            .get()
            .addOnSuccessListener {
                if(it != null) {
                    textViewProductNameIndividual.text = it.getString(("name")).toString()
                    textViewProductPriceIndividual.text = it.getString(("price").toString()) as String
                    Picasso.get()
                        .load(it.getString(("imageUrl")))
                        .into(imageViewProductImageIndividual)
                    textViewProductDescriptionIndividual.text = it.getString(("description")) as String
                }
            }
    }


}