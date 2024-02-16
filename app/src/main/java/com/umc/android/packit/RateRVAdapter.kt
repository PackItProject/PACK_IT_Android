package com.umc.android.packit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.android.packit.databinding.ItemRateBinding

class RateRVAdapter(private val grades: List<Grade>) : RecyclerView.Adapter<RateRVAdapter.ViewHolder>() {

interface MyItemClickListener {
}

    private lateinit var mItemClickListener: MyItemClickListener

        fun setMyItemClickListener(itemClickListener: RateFragment) {
        mItemClickListener = itemClickListener
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RateRVAdapter.ViewHolder {
        val binding: ItemRateBinding =
            ItemRateBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: RateRVAdapter.ViewHolder, position: Int) {
        holder.bind(grades[position], holder.itemView.context)
        }

        override fun getItemCount(): Int = grades.size

    inner class ViewHolder(val binding: ItemRateBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(grade: Grade, context: Context) {
        binding.itemRateUsernameTv.text = grade.nickname
        binding.itemRateContentTv.text = grade.content
        binding.itemRateNumTv.text = "("+grade.grade+")"
            if (grade.image != null) {
                Glide.with(context)
                    .load(grade.image)
                    .into(binding.itemRateImg1Iv)
                binding.itemRateHorizontalSv.visibility = View.VISIBLE
            } else {
                binding.itemRateHorizontalSv.visibility = View.GONE
            }
        }
    }
}