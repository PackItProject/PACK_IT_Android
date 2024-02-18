package com.umc.android.packit

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        holder.bind(menus[position], holder.itemView.context)

        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(menus[position])
        }

    }

    override fun getItemCount(): Int = menus.size

    inner class ViewHolder(val binding: ItemMainMenuBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(menu: Menu, context: Context) {
                binding.itemMainTitleTv.text = menu.menu_name
                binding.itemMainPriceTv.text = menu.price.toString()+"원"
                binding.itemMainSizeTv.text = menu.containter

                menu.image?.let {
                    Glide.with(context)
                        .load(it)
                        .into(binding.itemMainImgIv)
                }


        }
    }
}
