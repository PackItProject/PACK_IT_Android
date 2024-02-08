package com.umc.android.packit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderHistoryRVAdapter(private val orderHistoryList: List<OrderHistoryMenu>) : RecyclerView.Adapter<OrderHistoryRVAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.item_history_date_tv)
        val storeNameTextView: TextView = itemView.findViewById(R.id.item_history_name_tv)
        val reservationTimeTextView: TextView = itemView.findViewById(R.id.item_history_time_tv)
        val menuTextView: TextView = itemView.findViewById(R.id.item_history_menu_tv)
        val priceTextView: TextView = itemView.findViewById(R.id.item_history_price_tv)
        val stateTextView: TextView = itemView.findViewById(R.id.item_history_state_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderItem = orderHistoryList[position]

        // 데이터와 뷰를 연결
        holder.dateTextView.text = orderItem.date
        holder.storeNameTextView.text = orderItem.storeName
        holder.reservationTimeTextView.text = orderItem.reservationTime
        holder.menuTextView.text = orderItem.menu
        holder.priceTextView.text = orderItem.price
        holder.stateTextView.text = orderItem.state
    }

    override fun getItemCount(): Int {
        return orderHistoryList.size
    }
}
