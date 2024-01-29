package com.umc.android.packit
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MypackitPinAdapter(private val pinList: List<MypackitPinItem>) :
    RecyclerView.Adapter<MypackitPinAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pinImageView: ImageView = itemView.findViewById(R.id.badgeImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.mypackitPinName)
        val descriptionTextView: TextView = itemView.findViewById(R.id.mypackitPinDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mypackit_pin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pinItem = pinList[position]

        holder.pinImageView.setImageResource(pinItem.imageResourceId)
        holder.titleTextView.text = pinItem.title
        holder.descriptionTextView.text = pinItem.description
    }

    override fun getItemCount(): Int = pinList.size
}

