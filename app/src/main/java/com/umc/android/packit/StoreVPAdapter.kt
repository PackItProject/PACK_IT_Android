package com.umc.android.packit

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

import androidx.appcompat.app.AppCompatActivity

class StoreVPAdapter(private val activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val storeId = (activity as StoreActivity).storeId

        val bundle = Bundle().apply {
            putInt("storeId", storeId)
        }

        return when(position){
            0 -> {
                val fragment = MenuFragment()
                fragment.arguments = bundle
                fragment
            }
            1 -> {
                val fragment = StoreinfoFragment()
                fragment.arguments = bundle
                fragment
            }
            else -> {
                val fragment = RateFragment()
                fragment.arguments = bundle
                fragment
            }
        }
    }
}
