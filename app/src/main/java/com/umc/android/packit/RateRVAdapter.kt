package com.umc.android.packit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        holder.bind(grades[position])
        }

        override fun getItemCount(): Int = grades.size

    inner class ViewHolder(val binding: ItemRateBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(grade: Grade) {
        binding.itemRateUsernameTv.text = grade.user_name
        binding.itemRateContentTv.text = grade.content
        binding.itemRateNumTv.text = "("+grade.grade+")"
            grade.gradeImg?.let {
        binding.itemRateImg1Iv.setImageResource(it)
        }
        }
    }
}