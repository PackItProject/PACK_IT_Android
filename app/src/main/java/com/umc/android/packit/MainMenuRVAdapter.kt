package com.umc.android.packit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.android.packit.databinding.ItemMainMenuBinding
class MainMenuRVAdapter(private val menus: List<Menu>) : RecyclerView.Adapter<MainMenuRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onItemClick(menu: Menu)

    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MenuFragment) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MainMenuRVAdapter.ViewHolder {
        val binding: ItemMainMenuBinding =
            ItemMainMenuBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menus[position])

        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(menus[position])
        }

    }

    override fun getItemCount(): Int = menus.size

    inner class ViewHolder(val binding: ItemMainMenuBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(menu: Menu) {
            if (menu.category == 1){
                binding.itemMainTitleTv.text = menu.menu_name
                binding.itemMainPriceTv.text = menu.price.toString()
                binding.itemMainSizeTv.text = menu.containers

                menu.menuImg?.let {
                    binding.itemMainImgIv.setImageResource(it)
                }
            }
            else {
                binding.itemMainTitleTv.text = menu.menu_name
                binding.itemMainPriceTv.text = menu.price.toString()
                binding.itemMainSizeTv.text = menu.containers

                menu.menuImg?.let {
                    binding.itemMainImgIv.setImageResource(it)
                }
            }

        }
    }
}
