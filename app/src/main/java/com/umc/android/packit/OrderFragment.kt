package com.umc.android.packit

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import com.umc.android.packit.databinding.FragmentOrderBinding


class OrderFragment : AppCompatActivity() {

    // 변수 선언
    private lateinit var binding: FragmentOrderBinding
    private var isClicked = false // 쿠폰 버튼
    private var isChecked = false // 체크 버튼
    private lateinit var couponBackground: CardView // 쿠폰 배경


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // xml 바인딩 작업
        binding = FragmentOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // couponBackground 초기화 -> init으로 묶ㄱ기
        couponBackground = binding.orderCouponBackgroundView


        couponBackground.visibility = View.INVISIBLE

        binding.orderCheckOffBtnIv.visibility = View.VISIBLE
        binding.orderCheckOnBtnIv.visibility = View.GONE


        binding.orderCouponSelectedBtn.setOnClickListener {
            showCouponList()
        }

        // 하단 체크 버튼
        binding.orderCheckOffBtnIv.setOnClickListener {
            checkButtonState()
        }
        binding.orderCheckOnBtnIv.setOnClickListener {
            checkButtonState()
        }
    }

    private fun showCouponList() {
        isClicked = !isClicked // 상태 전환
        val slideDown = ObjectAnimator.ofFloat(couponBackground, "translationY", 0f, 30f)
         val slideUp = ObjectAnimator.ofFloat(couponBackground, "translationY", 30f, 0f)

        if (isClicked) {
                couponBackground.visibility = View.VISIBLE

            slideDown.duration = 300
            slideDown.start()
        } else {
            // 위로 슬라이드
            slideUp.duration = 300
            slideUp.start()
//            // 체크되지 않은 상태로 변경
            slideUp.doOnEnd {
                couponBackground.visibility = View.GONE
            }
        }
    }

    // 체크 버튼 on/off 함수
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
