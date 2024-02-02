package com.umc.android.packit

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class StoreVPAdapter(fragment: StoreActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MenuFragment()
            1 -> StoreinfoFragment()
            else -> RateFragment()
        }
    }
}