package com.umc.android.packit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.android.packit.databinding.ItemFavoriteBinding

class FavoriteRVAdapter(private val stores: List<Store>) : RecyclerView.Adapter<FavoriteRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onItemClick(store: Store)

        fun onStarClick(store: Store)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoriteRVAdapter.ViewHolder {
        val binding: ItemFavoriteBinding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteRVAdapter.ViewHolder, position: Int) {
        holder.bind(stores[position])

        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(stores[position])
        }

        holder.binding.itemFavoriteStarIv.setOnClickListener {
            mItemClickListener.onStarClick(stores[position])
        }
    }

    override fun getItemCount(): Int = stores.size

    @SuppressLint("NotifyDataSetChanged")
 /*   fun addStores(stores: ArrayList<Store>) {
        this.stores.clear()
        this.stores.addAll(stores)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeStore(position: Int) {
        stores.removeAt(position)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")*/
    fun updateStoreStarImage(store: Store) {
        val position = stores.indexOf(store)
        if (position != -1) {
            notifyItemChanged(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getFavoriteCount(): Int {
        return stores.count { it.star == true }
    }

    inner class ViewHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(store: Store) {
            binding.itemFavoriteNameTv.text = store.name
            binding.itemFavoriteAddressTv.text = store.address
            binding.itemFavoriteCloseTv.text = "("+store.state+")"
            binding.itemFavoriteRateTv.text = store.rate
            binding.itemFavoriteStateTv.text = store.state
            if (store.star==true){
                binding.itemFavoriteStarIv.setImageResource(R.drawable.btn_star_select)
            }
            else {
                binding.itemFavoriteStarIv.setImageResource(R.drawable.btn_star_no_select)
            }
            store.storeImg?.let {
                binding.itemFavoriteImgIv.setImageResource(it)
            }
        }
    }
}