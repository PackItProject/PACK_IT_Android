package com.example.packit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.packit.databinding.ItemStoreListBinding

class StoreListRVAdapter(private val stores: ArrayList<Store>) : RecyclerView.Adapter<StoreListRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): StoreListRVAdapter.ViewHolder {
        val binding: ItemStoreListBinding =
            ItemStoreListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stores[position])

    }

    override fun getItemCount(): Int = stores.size

    @SuppressLint("NotifyDataSetChanged")
    fun addStores(stores: ArrayList<Store>) {
        this.stores.clear()
        this.stores.addAll(stores)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeStore(position: Int) {
        stores.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemStoreListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(store: Store) {
            binding.itemListNameTv.text = store.name
            binding.itemListAddressTv.text = store.address
            binding.itemListCloseTv.text = store.state
            binding.itemListRateTv.text = store.rate
            binding.itemListStateTv.text = store.state
            store.storeImg?.let {
                binding.itemListImgIv.setImageResource(it)
            }
        }
    }
}
