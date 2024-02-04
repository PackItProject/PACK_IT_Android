package com.umc.android.packit
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MypackitPinAdapter(private val itemList: List<MypackitItem>) : RecyclerView.Adapter<MypackitPinAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val badgeImageView: ImageView = itemView.findViewById(R.id.badgeImageView)
        val mypackitPinName: TextView = itemView.findViewById(R.id.mypackitPinName)
        val mypackitPinDate: TextView = itemView.findViewById(R.id.mypackitPinDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mypackit_pin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        // 데이터와 뷰를 연결
        holder.mypackitPinName.text = item.mypackitPinName
        holder.mypackitPinDate.text = item.mypackitPinDate
        holder.badgeImageView.setImageResource(item.badgeImageResource)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}