package com.umc.android.packit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.umc.android.packit.databinding.ActivityProfileBinding
import com.umc.android.packit.databinding.FragmentOrderBinding


class OrderFragment : AppCompatActivity() {

    // 변수 선언
    private lateinit var binding: FragmentOrderBinding
    private var isChecked = false // 초기 상태: 언체크

    override fun onCreate(savedInstanceState: Bundle?) {
        // xml 바인딩 작업
        binding = FragmentOrderBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // 하단 체크 버튼
        binding.orderCheckOffBtnIv.setOnClickListener {
            checkButtonState()
        }
        binding.orderCheckOnBtnIv.setOnClickListener {
            checkButtonState()
        }
    }

    private fun checkButtonState() {
        isChecked = !isChecked // 상태 전환

        if (isChecked) {
            // 체크된 상태로 변경
            binding.orderCheckOffBtnIv.visibility = View.GONE
            binding.orderCheckOnBtnIv.visibility = View.VISIBLE
        } else {
            // 체크되지 않은 상태로 변경
            binding.orderCheckOffBtnIv.visibility = View.VISIBLE
            binding.orderCheckOnBtnIv.visibility = View.GONE
        }
    }
}