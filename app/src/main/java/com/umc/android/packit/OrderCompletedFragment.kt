package com.umc.android.packit

import android.content.Context
import android.content.SharedPreferences
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.umc.android.packit.databinding.FragmentOrderCompletedBinding
import androidx.fragment.app.FragmentTransaction
import com.umc.android.packit.databinding.FragmentOrderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 프래그먼트 상속
class OrderCompletedFragment: DialogFragment() {
    var orderMenus = ArrayList<OrderMenu>()
    var userId: Int = 1
    var store_id: Int = 0
    lateinit var binding: FragmentOrderCompletedBinding
    private var orderDatas = ArrayList<OrderHistoryMenu>() //주문기록 리스트
    private lateinit var orderHistoryRVAdapter: OrderHistoryRVAdapter // 변수 선언
    var orderCount:Int=0 //주문했던 기록의 횟수
    var cartTimeData: String = ""
    var cartPriceData: Int =0

    companion object {
        private const val CART_TIME_KEY = "cartTimeKey"
/*        private const val ORDER_STORE_NAME_KEY = "orderStoreNameKey"*/
        fun newInstance(cartTimeData: String, /*orderStoreName: String?*/): OrderCompletedFragment {
            val fragment = OrderCompletedFragment()
            val args = Bundle()
            args.putString(CART_TIME_KEY, cartTimeData)
         /*   args.putString(ORDER_STORE_NAME_KEY, orderStoreName)*/
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false // 팝업창 외 터치 X

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderCompletedBinding.inflate(inflater, container, false)
        val view = binding.root

        //sharedpreference에 저장되어있던 주문기록 횟수 값을 찾아 갱신
        val sharedPreference = requireActivity().getSharedPreferences("sp1", Context.MODE_PRIVATE)
        orderCount = sharedPreference.getInt("orderCount", 0) // Retrieve the orderCount from SharedPreferences

        orderCount += 1 // 증가
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putInt("orderCount", orderCount)
        Log.d("0220put", orderCount.toString())
        editor.apply()

        //다이얼로그의 주문 시간에 오더액티비티의 주문 시간을 반영
        val cartTimeData = arguments?.getString(CART_TIME_KEY)

        //다이얼로그의 가게 이름의 오더액터비티의 가게 이름을 반영
       /* val orderStoreName = arguments?.getString(ORDER_STORE_NAME_KEY)*/
        if (cartTimeData!=null){
            binding.orderCompletedNoticedTimeTv.text = "${cartTimeData}분  "
        }
        else{
            binding.orderCompletedNoticedTimeTv.text="오후 12:30분 "
        }

 /*       if (orderStoreName != null) {
            Log.d("ykcompletedName",orderStoreName)
        }
        if (orderStoreName!=null){
            binding.orderCompletedNoticedTimeTv.text = orderStoreName
        }
        else{
            binding.orderCompletedNoticedTimeTv.text="가게이름 "
        }*/


        /*//OrderHistoryRVAdapter 초기화
        val recyclerView: RecyclerView =
            orderBinding.root.findViewById(R.id.history_main_recyclerView)
        orderHistoryRVAdapter = OrderHistoryRVAdapter(ArrayList()) //OrderHistoryMenu를 비어있는 리스트로 초기화
        orderHistoryRVAdapter.setMyItemClickListener(this)
        recyclerView.adapter = orderHistoryRVAdapter

        val apiService = ApiClient.retrofitInterface
        val userId = 1 // 사용자 ID를 여기에 설정하세요*/

        // 팝업 닫기 버튼 클릭
        binding.orderCompletedClosedBtnIv.setOnClickListener {
            dismiss() //주문완료 팝업창 닫기
        }

        // 주문 상세 버튼 클릭-> 가게 정보 프래그먼트로 페이지 이동
        binding.orderCompletedOrderDetailedBtn.setOnClickListener {
            dismiss() //주문완료 팝업창 닫기

            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            val orderHistoryFragment=OrderHistoryFragment()
            transaction.replace(
                R.id.container,
                orderHistoryFragment
            )  //activity_main.xml에서 맨 아래에 오는 화면을 orderHistoryFragment로 교체함

            transaction.commit()

            Toast.makeText(requireActivity(), "주문 내역 화면으로 이동합니다.", Toast.LENGTH_SHORT).show()
        }

        //주문 취소 버튼 클릭
        binding.orderCompletedOrderDeletedBtn.setOnClickListener {
            dismiss() //주문완료 팝업창 닫기
            orderCount -=1 //주문취소했으니 주문기록 횟수 감소

            //TODO:주문삭제 레트로핏
            //서버에 상태 업데이트 요청 전송
            val apiService = ApiClient.retrofitInterface

          /*  store_id = intent.getIntExtra("storeId",0)
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
*/
            /*val deleteOrderCall =apiService.deleteOrder(orderRequest)

            deleteOrderCall.enqueue(object : Callback<DeleteOrderResponse> {
                override fun onResponse(call: Call<DeleteOrderResponse>, response: Response<DeleteOrderResponse>) {
                    if (response.isSuccessful) {
                        val deleteOrderResponse = response.body()
                        deleteOrderResponse?.let{ // 성공적으로 주문이 삭제되었을 때
                            Toast.makeText(requireActivity(), "주문이 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        // 주문 삭제 실패 시의 처리
                        Toast.makeText(requireActivity(), "주문이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DeleteOrderResponse>, t: Throwable) {
                    // 네트워크 오류 등으로 호출에 실패했을 때
                    Toast.makeText(requireActivity(), "서버 에러: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })*/

        }

        return view
    }
}