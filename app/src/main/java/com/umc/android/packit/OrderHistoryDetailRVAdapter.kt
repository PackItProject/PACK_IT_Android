package com.umc.android.packit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class OrderHistoryDetailedRVAdapter(
    private val orderDetails: List<OrderHistoryDetail>
) : RecyclerView.Adapter<OrderHistoryDetailedRVAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuNameTextView = itemView.findViewById<TextView>(R.id.item_order_detailed_menu_name_tv)
        val priceTextView = itemView.findViewById<TextView>(R.id.item_order_detailed_menu_price_tv)
        val paymentTextView =itemView.findViewById<TextView>(R.id.item_order_detailed_menu_count_tv)
        val menuImageView = itemView.findViewById<ImageView>(R.id.item_order_detailed_menu_image_iv)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_detailed_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderDetail = orderDetails[position]

        holder.menuNameTextView.text = orderDetail.menu_name
        holder.priceTextView.text = "${orderDetail.price} 원"
        holder.paymentTextView.text = "${orderDetail.payment}"

        Glide.with(holder.menuImageView.context)
            .load(orderDetail.image)
            .into(holder.menuImageView)

    }

    override fun getItemCount(): Int {
        return orderDetails.size
    }


}
