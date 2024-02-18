package com.umc.android.packit

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.umc.android.packit.databinding.FragmentOrderCompletedBinding
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Index
import com.umc.android.packit.ApiClient.retrofitInterface
import com.umc.android.packit.databinding.FragmentOrderBinding
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 프래그먼트 상속
class OrderCompletedFragment: DialogFragment() {

    lateinit var binding: FragmentOrderCompletedBinding

    lateinit var orderBinding: FragmentOrderBinding
    private var orderDatas = ArrayList<OrderHistoryMenu>() //주문기록 리스트
    private lateinit var orderHistoryRVAdapter: OrderHistoryRVAdapter // 변수 선언
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
            val storeInfoFragment = StoreinfoFragment()
            transaction.replace(
                R.id.container,
                storeInfoFragment
            )  //activity_main.xml에서 맨 아래에 오는 화면을 storeInfoFragment로 교체함

            transaction.commit()

            Toast.makeText(requireActivity(), "가게 정보 화면으로 이동합니다.", Toast.LENGTH_SHORT).show()
        }

        //주문 취소 버튼 클릭
        binding.orderCompletedOrderDeletedBtn.setOnClickListener {
            dismiss() //주문완료 팝업창 닫기

          /*  //TODO:주문삭제 레트로핏
            // 주문 삭제 호출
            val orderIdToDelete = 1 // Replace this with the actual order ID to delete
            val deleteOrderCall = retrofitInterface.deleteOrder(orderIdToDelete)

            deleteOrderCall.enqueue(object : Callback<DeleteOrderResponse> {
                override fun onResponse(call: Call<DeleteOrderResponse>, response: Response<DeleteOrderResponse>) {
                    if (response.isSuccessful) {  // 성공적으로 주문이 삭제되었을 때
                        val deleteOrderResponse = response.body()
                        Toast.makeText(requireActivity(), "주문이 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show()
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