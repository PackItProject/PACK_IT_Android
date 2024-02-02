package com.umc.android.packit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.umc.android.packit.databinding.FragmentOrderBinding
import com.umc.android.packit.databinding.FragmentOrderHistoryDetailedBinding

class OrderHistoryDetailedFragment:AppCompatActivity() {
    lateinit var binding: FragmentOrderHistoryDetailedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentOrderHistoryDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}