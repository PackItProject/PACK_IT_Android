package com.umc.android.packit

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.android.packit.databinding.FragmentOrderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class OrderActivity() : AppCompatActivity() {

    // 변수 선언
    private lateinit var binding: FragmentOrderBinding
    private lateinit var couponBackground: CardView // 쿠폰 배경
    private var isClicked = false // 쿠폰 버튼 상태
    private var isChecked = false // 체크 버튼 상태
    private var orderDatas=ArrayList<OrderMenu>()
    var cartTimeData: String = ""
    var store_id: Int = 0
    var cartPriceData: Int =0
    var userId: Int = 1
    var orderMenus = ArrayList<OrderMenu>()
    var orderStoreName: String =""



    private val couponList = listOf(
        OrderCoupon("[첫 주문] 5% 할인",0.05),
        OrderCoupon("[누적 주문 5회] 10% 할인",0.05),
        OrderCoupon("[누적 주문 10회] 15% 할인",0.15),
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


        val requestText = binding.orderRequestEt.text.toString()

        // RadioButton 선택 여부 확인 및 값 가져오기
        val selectedPaymentMethodId = binding.orderRadioGroup.checkedRadioButtonId
        var selectedPaymentMethod = 0

        // RadioButton 클릭 리스너 설정
        binding.orderPaymentCardTv.setOnClickListener {
            // 클릭된 RadioButton에 대한 처리
            selectedPaymentMethod = 1
        }

        binding.orderPaymentMobileTv.setOnClickListener {
            // 클릭된 RadioButton에 대한 처리
            selectedPaymentMethod = 2
        }

        binding.orderPaymentKakaoTv.setOnClickListener {
            // 클릭된 RadioButton에 대한 처리
            selectedPaymentMethod = 3
        }

        binding.orderPaymentNaverTv.setOnClickListener {
            // 클릭된 RadioButton에 대한 처리
            selectedPaymentMethod = 4
        }


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

            // 주문하기 버튼 누르면 주문추가 API 처리
            // 서버에 상태 업데이트 요청 전송
            val apiService = ApiClient.retrofitInterface

            store_id = intent.getIntExtra("storeId",0)
            orderMenus = (intent.getSerializableExtra("orderMenu") as? ArrayList<OrderMenu>)!!
            val orderRequest = OrderRequest(
                pk_user = userId,
                store_id = store_id,
                requirement = requestText,
                payment = selectedPaymentMethod,
                pickup_time = cartTimeData,
                status = 1,
                menus = orderMenus,
                fee = cartPriceData
            )

            // 클릭한 메뉴 아이템의 유저 id, 가게 id, 메뉴 id 가져온 후, 전송
            for (menu in orderMenus) {
                val deleteRequest = DeleteCartRequest(userId, store_id, menu.menu_id)
                apiService.subMenuToCart(deleteRequest).enqueue(object : Callback<BookmarkResponse> {
                    override fun onResponse(call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                        if (response.isSuccessful) {
                            when (response.code()) {
                                200 -> {
                                    Toast.makeText(
                                        this@OrderActivity,
                                        "메뉴가 성공적으로 삭제되었습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                404 -> {
                                    // 실패 1
                                    Toast.makeText(
                                        this@OrderActivity,
                                        "${response.code()}: 장바구니에 해당 아이템이 존재하지 않습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                500 -> {
                                    // 실패 2
                                    Toast.makeText(
                                        this@OrderActivity,
                                        "${response.code()}: 장바구니에서 아이템을 삭제하는데 실패하였습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            // 응답이 실패한 경우
                            Toast.makeText(
                                this@OrderActivity,
                                "장바구니 메뉴 삭제에 실패하였습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                        // 네트워크 오류 등 호출 실패 시 처리
                        Toast.makeText(
                            this@OrderActivity,
                            "API 호출에 실패했습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }

            Log.d("selected card: ",selectedPaymentMethod.toString())
            val addOrderCall = apiService.addOrder(orderRequest)

            addOrderCall.enqueue(object : Callback<AddOrderResponse> {
                override fun onResponse(call: Call<AddOrderResponse>, response: Response<AddOrderResponse>) {

                    if (response.isSuccessful) {
                        val addOrderResponse = response.body()
                        addOrderResponse?.let {
                            // 성공적으로 주문이 추가되었을 때의 처리


                            Toast.makeText(this@OrderActivity, "정상적으로 주문되었습니다.", Toast.LENGTH_SHORT).show()



                        }

                    } else {
                        // 주문 추가 실패 시의 처리

                        Toast.makeText(this@OrderActivity, "주문추가에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                        Log.e("OrderActivity", "Unsuccessful response: ${response.code()}")
                        try {
                            val errorBody = response.errorBody()?.string()
                            Log.e("OrderActivity", "Error body: $errorBody")
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                    }
                }

                override fun onFailure(call: Call<AddOrderResponse>, t: Throwable) {
                    // 네트워크 오류 등으로 호출에 실패했을 때의 처리
                    Toast.makeText(this@OrderActivity, "onFailure.", Toast.LENGTH_SHORT).show()
                }
            })

        }

    }

    // 화면 초기화 함수
    private fun init() {

        binding.orderStoreNameTv.text = intent.getStringExtra("storeName")
        binding.orderStoreAddressTv.text = intent.getStringExtra("storeAdd")

        // 쿠폰 리스트 숨김 상태
        couponBackground = binding.orderCouponBackgroundView
        couponBackground.visibility = View.INVISIBLE

        // 체크 버튼 해제 상태
        binding.orderCheckOffBtnIv.visibility = View.VISIBLE
        binding.orderCheckOnBtnIv.visibility = View.GONE



        // SharedPreference에서 닉네임을 불러와서 orderUserNameTv에 표시
        val sharedPreference = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val nickname = sharedPreference.getString("name", "데이터 없음")
        binding.orderUserNameTv.text = nickname

        //cartFragment에서 전달한 픽업 시간과 총 결제 금액 띄우기
        cartTimeData = intent.getStringExtra("cartTimeKey")!!

        //TODO:
  /*      orderStoreName = intent.getStringExtra("orderStoreName")!!
*/
        binding.orderPickUp02Tv.text = cartTimeData  //주문 시간 데이터 전달
        val orderCompletedFragment = OrderCompletedFragment.newInstance(cartTimeData) //orderCompletedFragment로도 전달


        cartPriceData = intent.getIntExtra("cartPriceKey", 0)!!
        binding.orderTotalPrice02Tv.text =cartPriceData.toString()+" 원"  //총 결제 금액 데이터 전달
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
            intent.putExtra("orderTime", cartTimeData)

        /*    //TODO
            //스토어네임을 오더액티비티->메인액티비티->오더컴플리티드프래그먼트로 전달
            intent.putExtra("orderStoreName", binding.orderStoreNameTv.text.toString())
            Log.d("ykorderActivity storename",binding.orderStoreNameTv.text.toString())
*/

            // 플래그 설정 (지금까지의 액티비티 초기화)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("showOrderCompletedFragment", true)

            startActivity(intent)
        }
    }
    private fun updateTotalPriceWithDiscount(discountPercent: Double) {
        //인텐트로 할인적용 전 가격 가져오기
        val originalPrice: Int = intent.getIntExtra("cartPriceKey", 0)
        //할인적용
        val discountedTotalPrice = originalPrice.toDouble() * (1 - discountPercent)
        //10원단위에 맞추기 (절사형: 2959원-> 2950원)
        val roundedTotalPrice = (discountedTotalPrice / 10).toInt() * 10
        //텍스트뷰에 할인적용금액 보여주기
        binding.orderTotalPrice02Tv.text = "${roundedTotalPrice}원"
    }


}
