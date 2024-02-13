package com.umc.android.packit

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.android.packit.databinding.FragmentOrderBinding


class OrderActivity() : AppCompatActivity() {

    // 변수 선언
    private lateinit var binding: FragmentOrderBinding
    private lateinit var couponBackground: CardView // 쿠폰 배경
    private var isClicked = false // 쿠폰 버튼 상태
    private var isChecked = false // 체크 버튼 상태

    private val couponList = listOf(
        OrderCoupon("[첫 주문] 5% 할인",0.05),
        OrderCoupon("[누적 주문 10회] 5% 할인",0.05),
        OrderCoupon("[누적 주문 10회] 15% 할인",0.15),
        OrderCoupon("[누적 주문 10회] 20% 할인",0.2),
        OrderCoupon("쿠폰 미사용",0.0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()  // 화면 초기화

        // 리사이클러뷰 연결
        val adapter = OrderCouponRVAdapter(couponList)
        binding.orderCouponRecyclerView.adapter = adapter
        binding.orderCouponRecyclerView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        // 아이템 클릭 이벤트 설정
        // 클릭한 쿠폰으로 텍스트 변경
        adapter.itemClick  = object : OrderCouponRVAdapter.ItemClick {
            override fun onSelectCopon(position: Int) {
                binding.orderCouponSelectedBtn.text = couponList[position].name
                isClicked=false  // 클릭 버튼 상태 바꿔 주면서 쿠폰 리스트 숨기기
                hideCouponList()

                val discountPercent= couponList[position].discountPercent
                if (discountPercent != null) {
                    updateTotalPriceWithDiscount(discountPercent)
                }
            }
        }

        // 쿠폰 선택 버튼
        binding.orderCouponSelectedBtn.setOnClickListener {
            clickCouponSelection()
        }

        // 하단 체크 버튼
        binding.orderCheckOffBtnIv.setOnClickListener {
            checkButtonState()
        }
        binding.orderCheckOnBtnIv.setOnClickListener {
            checkButtonState()
        }

        // 뒤로 가기 버튼 -> 장바구니 화면으로 이동
        binding.orderBackBtnIv.setOnClickListener {
            finish()
        }

        // 주문하기 버튼 -> 체크 및 쿠폰 선택 검사
        binding.orderPaidBtn.setOnClickListener {
            checkStateValidity()
        }
    }

    // 화면 초기화 함수
    private fun init() {
        // 쿠폰 리스트 숨김 상태
        couponBackground = binding.orderCouponBackgroundView
        couponBackground.visibility = View.INVISIBLE

        // 체크 버튼 해제 상태
        binding.orderCheckOffBtnIv.visibility = View.VISIBLE
        binding.orderCheckOnBtnIv.visibility = View.GONE

        //cartFragment에서 전달한 픽업 시간과 총 결제 금액 띄우기
        val cartTimeData = intent.getStringExtra("cartTimeKey")
        binding.orderPickUp02Tv.text = cartTimeData  //주문 시간 데이터 전달

        val cartPriceData = intent.getStringExtra("cartPriceKey")
        binding.orderTotalPrice02Tv.text =cartPriceData  //총 결제 금액 데이터 전달
    }

    // 쿠폰 클릭 시, 쿠폰 리스트 띄우기
    private fun clickCouponSelection() {
        isClicked = !isClicked // 상태 전환
        if (isClicked) showCouponList() else hideCouponList()
    }

    // 쿠폰 리스트 보이기
    private fun showCouponList() {
        couponBackground.visibility = View.VISIBLE
        // 아래로 슬라이드
        val slideDown = ObjectAnimator.ofFloat(couponBackground, "translationY", 0f, 55f)
        slideDown.duration = 300
        slideDown.start()
    }

    // 쿠폰 리스트 숨기기
    private fun hideCouponList() {
        // 위로 슬라이드
        val slideUp = ObjectAnimator.ofFloat(couponBackground, "translationY", 55f, 0f)
        slideUp.duration = 300
        slideUp.start()
        slideUp.doOnEnd {
            couponBackground.visibility = View.GONE
        }
    }

    // 체크 버튼 on/off 함수
    private fun checkButtonState() {
        isChecked = !isChecked // 상태 전환

        if (isChecked) {    // 체크된 상태로 변경
            binding.orderCheckOffBtnIv.visibility = View.GONE
            binding.orderCheckOnBtnIv.visibility = View.VISIBLE
        } else {            // 체크되지 않은 상태로 변경
            binding.orderCheckOffBtnIv.visibility = View.VISIBLE
            binding.orderCheckOnBtnIv.visibility = View.GONE
        }
    }

    // 주문하기 버튼 유효성 검사
    private fun checkStateValidity() {
        if (binding.orderRadioGroup.checkedRadioButtonId == -1) {
            // 토글 버튼이 눌려있지 않은 경우
            Toast.makeText(this, "토글 버튼을 눌러주세요.", Toast.LENGTH_SHORT).show()
        } else if (binding.orderCouponSelectedBtn.text == "쿠폰 선택") {
            // 쿠폰 선택의 텍스트가 "쿠폰 선택"인 경우
            Toast.makeText(this, "쿠폰을 선택해주세요.", Toast.LENGTH_SHORT).show()
        } else if (!isChecked) {
            // 아래 체크 박스가 visible이 아닌 경우
            Toast.makeText(this, "체크 박스를 확인해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            // 모든 유효성 검사를 통과한 경우, 메인 화면으로 페이지 이동
            val intent = Intent(this, MainActivity::class.java)

            // 플래그 설정 (지금까지의 액티비티 초기화)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("showOrderCompletedFragment", true)

            startActivity(intent)
        }
    }
    private fun updateTotalPriceWithDiscount(discountPercent:Double) {
        //cartFragment에서 전달한 주문 총액을 string으로 받아와서 double로 변환
        val originalPriceString: String? = intent.getStringExtra("cartPriceKey")
        //ex.13000원->13000으로 원을 빼야 더블로 변환 가능
        val originalPrice: Double? = originalPriceString?.replace("[^0-9]".toRegex(), "")?.toDoubleOrNull()
        //할인율 적용
        val discountedTotalPrice = originalPrice?.times((1 - discountPercent))

        //쿠폰할인율이 적용된 총액을 텍스트뷰에 전달
        val formattedTotalPrice = String.format("%,.0f원", discountedTotalPrice ?: 0.0)
        binding.orderTotalPrice02Tv.text = formattedTotalPrice
    }
}
