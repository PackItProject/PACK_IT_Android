package com.umc.android.packit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MypackitPinAdapter(private val dataList: List<String>) :
    RecyclerView.Adapter<MypackitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MypackitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_mypackit_pin, parent, false)
        return MypackitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MypackitViewHolder, position: Int) {
        // Bind data to views in each item
        val itemText = dataList[position]
        // Set data to your UI components in MyViewHolder

        holder.textViewTitle.text = itemText
        holder.textViewDate.text = "Some Date"
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
