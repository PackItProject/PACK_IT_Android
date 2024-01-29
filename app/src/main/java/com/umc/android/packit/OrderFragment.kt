package com.umc.android.packit

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.umc.android.packit.databinding.FragmentOrderBinding


class OrderFragment : AppCompatActivity() {

    // 변수 선언
    private lateinit var binding: FragmentOrderBinding
    private var isChecked = false // 초기 상태: 언체크
    private var couponOptions = arrayOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        // xml 바인딩 작업
        binding = FragmentOrderBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 쿠폰 데이터 가져옴
        /*couponOptions = arrayOf("쿠폰 미사용", "[첫 주문] 5% 할인", "[누적 주문 10회] 5% 할인")
        // 스피너 설정
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, couponOptions)
        binding.orderCouponSpinner.adapter = adapter*/

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