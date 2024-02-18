package com.umc.android.packit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class OrderHistoryRVAdapter(
    private val orderHistoryList: List<OrderHistoryMenu>
) : RecyclerView.Adapter<OrderHistoryRVAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.item_history_date_tv)
        val storeNameTextView: TextView = itemView.findViewById(R.id.item_history_name_tv)
        val reservationTimeTextView: TextView = itemView.findViewById(R.id.item_history_time_tv)
        val menuTextView: TextView = itemView.findViewById(R.id.item_history_menu_tv)
        val menuImageView: ImageView = itemView.findViewById(R.id.item_history_img_iv)


        val detailButton: Button = itemView.findViewById(R.id.item_history_detail_btn)

        init {
            // 버튼 클릭 리스너 설정
            detailButton.setOnClickListener {
                val context = itemView.context

                // 여기서 OrderHistoryDetailedFragment 로 이동하는 코드를 작성
                val OrderHistoryDetailedFragment = OrderHistoryDetailedFragment()
                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                fragmentManager.beginTransaction()
                    .replace(R.id.container, OrderHistoryDetailedFragment)
                    .addToBackStack(null) // 여기에 addToBackStack 메소드 위치 수정
                    .commit()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_history, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderItem = orderHistoryList[position]

        holder.dateTextView.text = orderItem.date
        holder.storeNameTextView.text = orderItem.storeName
        holder.reservationTimeTextView.text = orderItem.reservationTime
        holder.menuTextView.text = "${orderItem.menu} 개"
        Glide.with(holder.itemView.context)
            .load(orderItem.image) // orderItem에서 이미지 URL을 가져와야 합니다.
            .into(holder.menuImageView)
    }

    override fun getItemCount(): Int {
        return orderHistoryList.size
    }
}
