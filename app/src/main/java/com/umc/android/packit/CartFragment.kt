package com.umc.android.packit

import android.os.Bundle
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.umc.android.packit.databinding.FragmentCartBinding

// 프래그먼트 상속
class CartFragment:AppCompatActivity() {
    lateinit var binding : FragmentCartBinding
    lateinit var timePicker: TimePicker

    private var pickUpAmPm : String = ""
    private var pickUpHour : Int = 0
    private var pickUpminute : Int = 0

    private val menuList = listOf(
        OrderMenu("힘", 1, 1),
        OrderMenu("내", 2200, 2),
        OrderMenu("자", 3, 3),
        OrderMenu("파이", 40000000, 4),
        OrderMenu("팅!", 90, 5)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding 초기화
        binding = FragmentCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 초기화

        initView()
        reservePickUp()

        // TODO: 메뉴아이템 어댑터 연결
        val adapter = CartRVAdapter(menuList)
        binding.menuListRecyclerView.adapter = adapter
        // 메뉴아이템 개수
    }

    // 현재 시각 기준으로 및 픽업 시간 초기화
    private fun initView() {
        // timePicker 초기화
        timePicker = binding.reservationTp

        // API 레벨에 따라 TimePicker의 시간을 가져옴
        val getHour = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            timePicker.hour
        } else {
            @Suppress("DEPRECATION")
            timePicker.currentHour
        }

        val getMinute = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            timePicker.minute
        } else {
            @Suppress("DEPRECATION")
            timePicker.currentMinute
        }

        // 오전/오후, 시, 분 저장
        pickUpAmPm = if (getHour < 12) "오전" else "오후"
        pickUpHour = getHour
        pickUpminute = getMinute

        // "오후 12:30" 꼴
        binding.receiptPickUp02Tv.text = String.format("%s %02d:%02d", pickUpAmPm, pickUpHour, pickUpminute)
    }

    // 타임 피커 시간 설정에 따라 픽업시간 텍스트 변경
    private fun reservePickUp() {
        timePicker.setOnTimeChangedListener{ _, hourOfDay, minute ->

            pickUpAmPm = if (hourOfDay < 12) "오전" else "오후"
            pickUpHour = if (hourOfDay % 12 == 0)  12 else (hourOfDay % 12)

            pickUpminute = minute

            // "오후 12:30" 꼴
            binding.receiptPickUp02Tv.text = String.format("%s %02d:%02d", pickUpAmPm, pickUpHour, pickUpminute)
        }
    }

}