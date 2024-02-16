package com.umc.android.packit

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.android.packit.databinding.ItemFavoriteBinding
import com.bumptech.glide.Glide

class FavoriteRVAdapter(private val stores: MutableList<Store>) : RecyclerView.Adapter<FavoriteRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onItemClick(store: Store)
        fun onStarClick(store: Store)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: FavoriteFragment) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemFavoriteBinding = ItemFavoriteBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stores[position], holder.itemView.context)

        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(stores[position])
        }

        holder.binding.itemFavoriteStarIv.setOnClickListener {
            mItemClickListener.onStarClick(stores[position])
        }
    }

    override fun getItemCount(): Int = stores.size

    @SuppressLint("NotifyDataSetChanged")
    fun addStore(store: Store) {
        stores.add(store)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeStore(store: Store) {
        val position = stores.indexOf(store)
        if (position != -1) {
            stores.removeAt(position)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateStoreStarImage(store: Store) {
        val position = stores.indexOf(store)
        if (position != -1) {
            notifyItemChanged(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getFavoriteCount(): Int {
        return stores.count { it.is_bookmarked == 1 }
    }

    inner class ViewHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(store: Store, context: Context) {
            binding.itemFavoriteNameTv.text = store.store_name
            binding.itemFavoriteAddressTv.text = store.address
            if (store.status == 1) {
                binding.itemFavoriteCloseTv.text = "(영업 중)"
                binding.itemFavoriteStateTv.text = "영업 중"
            } else {
                binding.itemFavoriteCloseTv.text = "(영업 종료)"
                binding.itemFavoriteStateTv.text = "영업 종료"
            }
            binding.itemFavoriteRateTv.text = "평점 %.1f".format(store.average_grade)
            if (store.is_bookmarked == 1) {
                binding.itemFavoriteStarIv.setImageResource(R.drawable.btn_star_select)
            } else {
                binding.itemFavoriteStarIv.setImageResource(R.drawable.btn_star_no_select)
            }
            store.image?.let {
                Glide.with(context)
                    .load(it)
                    .into(binding.itemFavoriteImgIv)
            }
        }
    }
}