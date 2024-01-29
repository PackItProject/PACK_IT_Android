package com.umc.android.packit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        holder.bind(menus[position])

        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(menus[position])
        }

    }

    override fun getItemCount(): Int = menus.size

    inner class ViewHolder(val binding: ItemSideMenuBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(menu: Menu) {
            if (menu.category == 1){
                binding.itemSideTitleTv.text = menu.menu_name
                binding.itemSidePriceTv.text = menu.price.toString()
                binding.itemSideSizeTv.text = menu.containers

                menu.menuImg?.let {
                    binding.itemSideImgIv.setImageResource(it)
                }
            }
            else {
                binding.itemSideTitleTv.text = menu.menu_name
                binding.itemSidePriceTv.text = menu.price.toString()
                binding.itemSideSizeTv.text = menu.containers

                menu.menuImg?.let {
                    binding.itemSideImgIv.setImageResource(it)
                }
            }

        }
    }
}
