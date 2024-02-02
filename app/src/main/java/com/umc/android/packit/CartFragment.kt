package com.umc.android.packit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc.android.packit.databinding.FragmentCartBinding

// 프래그먼트 상속
class CartFragment:AppCompatActivity() {
    lateinit var binding:FragmentCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}