package com.example.app_compras

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class UserAdapter (private val exampleList: List<User>) : RecyclerView.Adapter<UserAdapter.ExampleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_user, parent, false)

        return ExampleViewHolder(itemView)
    }

    override fun getItemCount(): Int = exampleList.size

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.textViewUserFirstName.text = currentItem.firstName
        holder.textViewUserLastName.text = currentItem.lastName
        holder.textViewUserEmail.text = currentItem.email
    }

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewUserFirstName: TextView = itemView.findViewById(R.id.firstname)
        val textViewUserLastName: TextView = itemView.findViewById(R.id.lastname)
        val textViewUserEmail: TextView = itemView.findViewById(R.id.email)
    }

}