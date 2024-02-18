package com.umc.android.packit

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.android.packit.databinding.ItemStoreListBinding

class StoreListRVAdapter(private val stores: ArrayList<StoreResponse>) : RecyclerView.Adapter<StoreListRVAdapter.ViewHolder>() {

    interface MyItemClickListener {

        fun onItemClick(store: StoreResponse)

        fun onStarClick(store: StoreResponse)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): StoreListRVAdapter.ViewHolder {
        val binding: ItemStoreListBinding =
            ItemStoreListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stores[position], holder.itemView.context)

        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(stores[position])
        }

        holder.binding.itemListStarIv.setOnClickListener {
            mItemClickListener.onStarClick(stores[position])
        }
    }

    override fun getItemCount(): Int = stores.size

    @SuppressLint("NotifyDataSetChanged")
    fun addStores(stores: List<StoreResponse>) {
        this.stores.clear()
        this.stores.addAll(stores)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeStore(position: Int) {
        stores.removeAt(position)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateStoreStarImage(store: StoreResponse) {
        val position = stores.indexOf(store)
        if (position != -1) {
            notifyItemChanged(position)
        }
    }

    inner class ViewHolder(val binding: ItemStoreListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(store: StoreResponse, context: Context) {
            binding.itemListNameTv.text = store.store_name
            binding.itemListAddressTv.text = store.address
            if (store.status == 1) {
                binding.itemListCloseTv.text = "영업 중"
                binding.itemListStateTv.text = "영업 중"
            } else {
                binding.itemListCloseTv.text = "영업 종료"
                binding.itemListStateTv.text = "영업 종료"
            }
            binding.itemListRateTv.text = "평점  %.1f".format(store.average_grade)

            if (store.is_bookmarked == 1){
                binding.itemListStarIv.setImageResource(R.drawable.btn_star_select)
            }
            else {
                binding.itemListStarIv.setImageResource(R.drawable.btn_star_no_select)
            }
            store.image?.let {
                Glide.with(context)
                    .load(it)
                    .into(binding.itemListImgIv)
            }
        }
    }
}