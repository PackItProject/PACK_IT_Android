package com.umc.android.packit

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.android.packit.databinding.ItemSideMenuBinding

class SideMenuRVAdapter(private val menus: List<Menu>) : RecyclerView.Adapter<SideMenuRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onItemClick(menu: Menu)

    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MenuFragment) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SideMenuRVAdapter.ViewHolder {
        val binding: ItemSideMenuBinding =
            ItemSideMenuBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menus[position], holder.itemView.context)

        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(menus[position])
        }

    }

    override fun getItemCount(): Int = menus.size

    inner class ViewHolder(val binding: ItemSideMenuBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(menu: Menu, context : Context) {

                binding.itemSideTitleTv.text = menu.menu_name
                binding.itemSidePriceTv.text = menu.price.toString()+"원"
                binding.itemSideSizeTv.text = menu.containter

                menu.image?.let {
                    Glide.with(context)
                        .load(it)
                        .into(binding.itemSideImgIv)
                }

        }
    }
}
