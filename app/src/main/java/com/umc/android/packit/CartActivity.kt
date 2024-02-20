package com.umc.android.packit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.umc.android.packit.databinding.FragmentCartBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 프래그먼트 상속

class CartActivity : AppCompatActivity() {
    lateinit var binding: FragmentCartBinding
    lateinit var timePicker: TimePicker

    private var pickUpAmPm: String = ""
    private var pickUpHour: Int = 0
    private var pickUpminute: Int = 0

    private var menuList = ArrayList<CartResponse>()
    var nowPos = 0
    var storeId =0

    // 장바구니 조회, 삭제 api 호출
    val api = ApiClient.retrofitInterface

    private var totalPrice : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding 초기화
        binding = FragmentCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 가게 이름 설정
        loadMenuList()

        // 픽업 피커 초기화
        initTimePicker()

        // 뒤로 가기 버튼 -> 가게 화면으로 이동
        binding.backBtnIv.setOnClickListener {
            finish()
        }

        // 주문하기 버튼 -> 주문하기 화면으로 이동
        binding.orderBtn.setOnClickListener {
            //카트->주문내역으로 시간과 가격 보내기
            val cartTimeData = binding.receiptPickUp02Tv.text.toString()// 장바구니 주문 시간 데이터 추출
            val cartPriceData = binding.receiptTotalPrice02Tv.text.toString()

            if (totalPrice==0){ //총 결제금액이 0원-> 화면 넘어가면 안됨
                Toast.makeText(this@CartActivity, "장바구니가 비었습니다.", Toast.LENGTH_SHORT).show()

            }else {
                val intent = Intent(this, OrderActivity::class.java)
                intent.putExtra("cartTimeKey", cartTimeData)
                intent.putExtra("cartPriceKey", totalPrice)
                intent.putExtra("fee", storeId)

                startActivity(intent)
            }
        }
    }

    private fun loadMenuList() {
        // 가게 이름 설정
        val storeName = intent.getStringExtra("storeName")
        binding.storeNameTv.text = storeName

        // 유저 아이디, 가게 아이디 필요
        val userId = 1
        val storeId = intent.getIntExtra("storeId", -1)
        Toast.makeText(this@CartActivity, "storeID: ${storeId}", Toast.LENGTH_SHORT).show()

        api.getCartMenus(userId, storeId).enqueue(object : Callback<List<CartResponse>> {
            override fun onResponse(call: Call<List<CartResponse>>, response: Response<List<CartResponse>>) {
                if (response.isSuccessful) {
                    val cartResponses: List<CartResponse>? = response.body()
                    // 리스트가 null이 아닌지 확인
                    if (cartResponses != null) {
                        // menuList에 있는 기존 데이터 지우고 새로운 데이터 추가
                        menuList.clear()
                        menuList.addAll(cartResponses)

                        connectRecyclerView(menuList)
                    }
                } else {
                    // 조회 실패
                    Toast.makeText(this@CartActivity,
                        "${response.code()}: 장바구니 조회에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CartResponse>>, t: Throwable) {
                // 네트워크 오류 등 호출 실패 시 처리
                Toast.makeText(this@CartActivity, "API 호출에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 리사이클러 뷰 연결
    private fun connectRecyclerView(menuList: ArrayList<CartResponse>) {
        val adapter = CartRVAdapter(menuList)

        // 연결
        binding.menuListRecyclerView.adapter = adapter

        // 연결 후, 최종 가격 업데이트
        updateTotalPrice()

        // 아이템 클릭 이벤트 등록
        adapter.onItemClickListener(object : CartRVAdapter.ItemClick{
            // 메뉴 삭제
            override fun onRemoveMenu(position: Int) {

                // 클릭한 메뉴 아이템의 유저 id, 가게 id, 메뉴 id 가져온 후, 전송
                val menu = menuList[position]
                api.subMenuToCart(DeleteCartRequest(menu.pk_user,menu.store_id,menu.menu_id)).enqueue(object : Callback<BookmarkResponse> {
                    override fun onResponse(call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                        if (response.isSuccessful) {
                            when (response.code()) {
                                200 -> {
                                    // 장바구니 메누 삭제 성공
                                    adapter.removeMenu(position)
                                    updateTotalPrice()
                                    Toast.makeText(this@CartActivity, "메뉴가 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                                }
                                404 -> {
                                    // 실패 1
                                    Toast.makeText(this@CartActivity, "${response.code()}: 장바구니에 해당 아이템이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                                }
                                500 -> {
                                    // 실패 2
                                    Toast.makeText(this@CartActivity, "${response.code()}: 장바구니에서 아이템을 삭제하는데 실패하였습니다.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            // 응답이 실패한 경우
                            Toast.makeText(this@CartActivity, "장바구니 메뉴 삭제에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                        // 네트워크 오류 등 호출 실패 시 처리
                        Toast.makeText(this@CartActivity, "API 호출에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                })
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

    private fun initTimePicker() {
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

        // 타임피커 이벤트 처리
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute -> setFormatTime(hourOfDay, minute) }
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
