package com.umc.android.packit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.android.packit.databinding.ItemOrderCouponBinding

class OrderCouponRVAdapter(private val couponList: List<OrderCoupon>)
    :RecyclerView.Adapter<OrderCouponRVAdapter.ViewHolder>() {
    interface ItemClick {  //클릭이벤트추가부분
        fun onClick(position : Int)
    }

//    private lateinit var itemClick : ItemClick  //클릭이벤트추가부분
     var itemClick: ItemClick? = null // nullable로 선언

    // 쿠폰 데이터 개수

    override fun getItemCount(): Int = couponList.size


    // 뷰홀더 생성

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): OrderCouponRVAdapter.ViewHolder {
        val binding: ItemOrderCouponBinding = ItemOrderCouponBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    // 뷰홀더 쿠폰 데이터 바인딩
    override fun onBindViewHolder(holder: OrderCouponRVAdapter.ViewHolder, position: Int) {
        holder.bind(couponList[position])
        holder.binding.itemCoupon.setOnClickListener {
            itemClick?.let {
                it.onClick(position)
            }
        }
    }


    inner class ViewHolder(val binding: ItemOrderCouponBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coupon: OrderCoupon){
            binding.itemCouponNameTv.text = coupon.name


        }
    }
}