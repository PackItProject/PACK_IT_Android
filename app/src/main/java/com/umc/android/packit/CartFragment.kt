package com.umc.android.packit

import android.os.Bundle
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.umc.android.packit.databinding.FragmentCartBinding

// 프래그먼트 상속
class CartFragment:AppCompatActivity() {
    lateinit var binding : FragmentCartBinding
    lateinit var timePicker : TimePicker

    private var pickUpAmPm : String = ""
    private var pickUpHour : Int = 0
    private var pickUpminute : Int = 0

    private var totalPrice : Int = 0

    private var menuList = ArrayList<OrderMenu>().apply {
        add(OrderMenu("menu1", 10000, 1))
        add(OrderMenu("menu2", 2000000, 2))
        add(OrderMenu("menu3", 3000, 3))
        add(OrderMenu("menu4", 4000, 4))
        add(OrderMenu("menu5!", 5000, 5))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding 초기화
        binding = FragmentCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 초기화

        initView()
        reservePickUp()
        updateTotalPrice()


        val adapter = CartRVAdapter(menuList)
        binding.menuListRecyclerView.adapter = adapter

        // 아이템 클릭 이벤트 등록
        adapter.onItemClickListener(object : CartRVAdapter.ItemClick{
            // 메뉴 삭제
            override fun onRemoveMenu(position: Int) {
                adapter.removeMenu(position)
                updateTotalPrice() // 메뉴 삭제 후 총 가격 업데이트
            }

            // 메뉴 수량 추가
            override fun onAddMenu(position: Int) {
                adapter.addMenu(position)
                updateTotalPrice() // 메뉴 수량 추가 후 총 가격 업데이트
            }

            // 메뉴 수량 빼기
            override fun onSubMenu(position: Int) {
                adapter.subMenu(position)
                updateTotalPrice() // 메뉴 수량 뺀 후 총 가격 업데이트
            }

        })
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
        pickUpHour = if (getHour % 12 == 0)  12 else (getHour % 12)
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

    // 총 결제금액 업데이트
    private fun updateTotalPrice() {
        totalPrice = 0
        for (menu in menuList) {
            totalPrice += menu.price * menu.count
        }

        binding.receiptTotalPrice02Tv.text = String.format("%,d 원", totalPrice)
    }
}