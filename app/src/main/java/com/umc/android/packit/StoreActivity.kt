package com.umc.android.packit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.android.packit.databinding.ActivityStoreBinding

class StoreActivity : AppCompatActivity() {

    lateinit var binding: ActivityStoreBinding

    private val information = arrayListOf("메뉴", "가게 정보", "평점")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storeAdapter = StoreVPAdapter(this)
        binding.storeContentVp.adapter = storeAdapter
        TabLayoutMediator(binding.storeContentTb, binding.storeContentVp) { tab, position ->
            tab.text = information[position]
        }.attach()
    }
}