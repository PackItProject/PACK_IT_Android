package com.umc.android.packit

import android.os.Bundle
import retrofit2.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.Response
import com.umc.android.packit.databinding.FragmentOrderHistoryBinding
import kotlinx.coroutines.launch
import java.io.IOException


class OrderHistoryFragment : Fragment() {

    private lateinit var adapter: OrderHistoryRVAdapter


    lateinit var binding: FragmentOrderHistoryBinding
    private var view: View? = null
    private var orderID:Int =2

//    val dummyList: List<OrderHistoryMenu> = listOf(
//        OrderHistoryMenu(
//            date = "2024.01.01 00:00",
//            imageUrl = R.drawable.store_img_1,
//            storeName = "가게 이름 1",
//            reservationTime = "예약 시간 1",
//            menu = "메뉴 1",
//            price = "10000원",
//            state = "주문 완료"
//        ),
//        OrderHistoryMenu(
//            date = "2024.02.01 12:00",
//            imageUrl = R.drawable.store_img_2,
//            storeName = "가게 이름 2",
//            reservationTime = "예약 시간 2",
//            menu = "메뉴 2",
//            price = "15000원",
//            state = "주문 취소"
//        ),
//        OrderHistoryMenu(
//            date = "2024.03.01 18:00",
//            imageUrl =  R.drawable.store_img_3,
//            storeName = "가게 이름 4",
//            reservationTime = "예약 시간 4",
//            menu = "메뉴 3",
//            price = "20000원",
//            state = "주문 완료"
//        ),
//        OrderHistoryMenu(
//            date = "2024.03.01 18:00",
//            imageUrl =  R.drawable.store_img_3,
//            storeName = "가게 이름 4",
//            reservationTime = "예약 시간 4",
//            menu = "메뉴 3",
//            price = "20000원",
//            state = "주문 완료"
//        ),
//        OrderHistoryMenu(
//            date = "2024.03.01 18:00",
//            imageUrl =  R.drawable.store_img_3,
//            storeName = "가게 이름 4",
//            reservationTime = "예약 시간 4",
//            menu = "메뉴 3",
//            price = "20000원",
//            state = "주문 완료"
//        )
//        // 이하 추가 데이터 추가 가능
//    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)

        val rootView = inflater.inflate(R.layout.fragment_order_history, container, false)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.history_main_recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(context)

//        recyclerView.adapter = OrderHistoryRVAdapter(dummyList)

        // 상세 내용 버튼을 XML에서 찾습니다.

        lifecycleScope.launch {
            try {
                val userId = 2// your user id
                val orderHistoryList: List<OrderHistoryMenu> = fetchOrderHistory(userId)
                val adapter = OrderHistoryRVAdapter(orderHistoryList)

                // RecyclerView Adapter 초기화

                recyclerView.adapter = adapter
            } catch (e: IOException) {
                // 네트워크 오류 처리
                Log.e("YourFragment", "Failed to fetch order history. Exception: ${e.message}", e)
            }
        }

        return rootView

    }

    private fun fetchData() {
        lifecycleScope.launch {
            try {
                val userId = 2 // your user id
                val orderHistory = fetchOrderHistory(userId)

                // 이제 orderHistory를 사용하여 UI 업데이트 등을 수행
            } catch (e: IOException) {
                // 네트워크 오류 처리
                Log.e("YourFragment", "Failed to fetch order history. Exception: ${e.message}", e)
            }
        }
    }

    private suspend fun fetchOrderHistory(userId: Int): List<OrderHistoryMenu> {
        return try {
            val response = ApiClient.retrofitInterface.getOrderLists(userId)

            if (response.isSuccessful) {
                val httpCode = response.code()
                Log.d("HTTP_SUCCESS", "Success with HTTP code: $httpCode")
                response.body() ?: emptyList()

            } else {
                val errorBody = response.errorBody()?.string()
                throw IOException("Failed to fetch order history. HTTP code: ${response.code()}, Error body: $errorBody")
            }
        } catch (e: Exception) {
            val exceptionMessage = e.message ?: "Unknown exception"
            throw IOException("Failed to fetch order history. Exception: $exceptionMessage", e)
        }
    }
//    private suspend fun fetchOrderHistory(userId: Int): List<OrderHistoryMenu> {
//        try {
//            // API 호출 로직을 여기에 구현
//            val call: Call<List<OrderHistoryMenu>> = ApiClient.retrofitInterface.getOrderLists(userId)
//            val response: retrofit2.Response<List<OrderHistoryMenu>> = call.execute()
//
//            if (response.isSuccessful) {
//                // HTTP 상태 코드가 200~299 사이인 경우
//                val responseBody = response.body()
//                if (responseBody != null) {
//                    return responseBody
//                } else {
//                    // API 응답이 null이면 빈 리스트 반환
//                    return emptyList()
//                }
//            } else {
//                // 실패한 경우
//                val errorBody = response.errorBody()?.string()
//                throw IOException("Failed to fetch order history. HTTP code: ${response.code()}, Error body: $errorBody")
//            }
//        } catch (e: Exception) {
//            // 예외 처리
//            val exceptionMessage = e.message ?: "Unknown exception"
//            Log.e("OrderHistory", "Failed to fetch order history. Exception: $exceptionMessage", e)
//            throw IOException("Failed to fetch order history. Exception: $exceptionMessage", e)
//
//        }

//        return try {
//            // API 호출 로직을 여기에 구현
//            val response: retrofit2.Response<List<OrderHistoryMenu>> = ApiClient.retrofitInterface.getOrderLists(userId)
//
//            if (response.isSuccessful) {
//                // HTTP 상태 코드가 200~299 사이인 경우
//                response.body() ?: emptyList()
//            } else {
//                // 실패한 경우
//                val errorBody = response.errorBody()?.string()
//                throw IOException("Failed to fetch order history. HTTP code: ${response.code()}, Error body: $errorBody")
//            }
//        } catch (e: Exception) {
//            // 예외 처리
//            val exceptionMessage = e.message ?: "Unknown exception"
//            Log.e("OrderHistory", "Failed to fetch order history. Exception: $exceptionMessage", e)
//            throw IOException("Failed to fetch order history. Exception: $exceptionMessage", e)
//        }



//    }






}
