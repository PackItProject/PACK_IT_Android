package com.umc.android.packit

import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.umc.android.packit.databinding.FragmentCartBinding

// 프래그먼트 상속
class CartFragment : AppCompatActivity() {
    lateinit var binding: FragmentCartBinding
    lateinit var timePicker: TimePicker

    private var pickUpAmPm: String = ""
    private var pickUpHour: Int = 0
    private var pickUpminute: Int = 0
    private var menuList = ArrayList<Menu>()
    var nowPos = 0
    var storeId =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding 초기화
        binding = FragmentCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences에서 메뉴 리스트를 가져와서 보여줌
        loadMenuList()
        initView()
        reservePickUp()

        // 메뉴 아이템 어댑터 연결
        val adapter = CartRVAdapter(menuList)
        binding.menuListRecyclerView.adapter = adapter
    }

    private fun loadMenuList() {
        val sharedPreferences = getSharedPreferences("Cart", MODE_PRIVATE)
        val menuListJson = sharedPreferences.getString("menuList", null)

        menuList = if (!menuListJson.isNullOrEmpty()) {
            // 기존에 저장된 JSON 데이터가 있을 때만 Gson을 사용하여 리스트로 변환
            Gson().fromJson(menuListJson, object : TypeToken<ArrayList<Menu>>() {}.type)
        } else {
            ArrayList() // 저장된 데이터가 없을 경우 빈 리스트 생성
        }
    }


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

    private fun reservePickUp() {
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            pickUpAmPm = if (hourOfDay < 12) "오전" else "오후"
            pickUpHour = if (hourOfDay % 12 == 0) 12 else (hourOfDay % 12)
            pickUpminute = minute

            // "오후 12:30" 꼴
            binding.receiptPickUp02Tv.text = String.format("%s %02d:%02d", pickUpAmPm, pickUpHour, pickUpminute)
        }
    }

    // 나머지 부분은 그대로 유지
}

