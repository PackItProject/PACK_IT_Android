package com.umc.android.packit

import android.content.Intent
import android.os.Bundle
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.umc.android.packit.databinding.FragmentCartBinding

// 프래그먼트 상속

class CartActivity : AppCompatActivity() {
    lateinit var binding: FragmentCartBinding
    lateinit var timePicker: TimePicker

    private var pickUpAmPm: String = ""
    private var pickUpHour: Int = 0
    private var pickUpminute: Int = 0

    private var menuList = ArrayList<Menu>()
    var nowPos = 0
    var storeId =0

    private var totalPrice : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding 초기화
        binding = FragmentCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences에서 메뉴 리스트를 가져와서 보여줌
        loadMenuList()
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

        // 뒤로 가기 버튼 -> 가게 화면으로 이동
        binding.backBtnIv.setOnClickListener {
            finish()
        }

        // 주문하기 버튼 -> 주문하기 화면으로 이동
        binding.orderBtn.setOnClickListener {
            //카트->주문내역으로 시간과 가격 보내기
            val cartTimeData = binding.receiptPickUp02Tv.text.toString()// 장바구니 주문 시간 데이터 추출
            val cartPriceData= binding.receiptTotalPrice02Tv.text.toString() //장바구니 주문 가격 데이터 추출

            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra("cartTimeKey", cartTimeData)
            intent.putExtra("cartPriceKey", cartPriceData)

            startActivity(intent)
        }
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
        setFormatTime(getHour, getMinute)
    }

    private fun reservePickUp() {
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute -> setFormatTime(hourOfDay, minute)
        }
    }

    private fun setFormatTime(hours:Int, minutes: Int) {
        // 오전/오후, 시, 분 저장
        pickUpAmPm = if (hours < 12) "오전" else "오후"
        pickUpHour = if (hours % 12 == 0) 12 else (hours % 12)
        pickUpminute = minutes

        // "오후 12:30" 꼴
        binding.receiptPickUp02Tv.text = String.format("%s %02d:%02d", pickUpAmPm, pickUpHour, pickUpminute)
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

