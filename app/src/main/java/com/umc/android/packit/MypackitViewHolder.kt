package com.umc.android.packit

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MypackitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val imageViewBadge: ImageView = itemView.findViewById(R.id.badgeImageView)

    val textViewTitle: TextView = itemView.findViewById(R.id.mypackitPinName)
    val textViewDate: TextView = itemView.findViewById(R.id.mypackitPinDate)


}
