package com.example.packit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.packit.databinding.FragmentStoreBinding
import com.google.android.material.tabs.TabLayoutMediator

class StoreFragment : Fragment() {

    lateinit var binding: FragmentStoreBinding

    private val information = arrayListOf("메뉴", "가게 정보", "평점")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreBinding.inflate(inflater,container,false)

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