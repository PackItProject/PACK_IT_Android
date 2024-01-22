package com.umc.android.packit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.android.packit.databinding.ActivityStoreBinding

class StoreActivity : Fragment() {

    lateinit var binding: ActivityStoreBinding

    private val information = arrayListOf("메뉴", "가게 정보", "평점")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityStoreBinding.inflate(inflater,container,false)

//        binding.storeBackIv.setOnClickListener {
//            (context as MainActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.main_frm, HomeFragment())
//                .commitAllowingStateLoss()
//        }


        val storeAdapter = StoreVPAdapter(this)
        binding.storeContentVp.adapter = storeAdapter
        TabLayoutMediator(binding.storeContentTb, binding.storeContentVp){
                tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }

}