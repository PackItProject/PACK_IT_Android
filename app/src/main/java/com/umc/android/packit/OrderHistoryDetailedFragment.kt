package com.umc.android.packit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc.android.packit.databinding.FragmentOrderBinding
import com.umc.android.packit.databinding.FragmentOrderHistoryDetailedBinding
import kotlinx.coroutines.launch
import java.io.IOException

class OrderHistoryDetailedFragment: Fragment() {
    lateinit var binding: FragmentOrderHistoryDetailedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_order_history, container, false)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.history_main_recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(context)

//        lifecycleScope.launch {
//            try {
//                val orderId = // your user id
//                val orderHistoryList: List<OrderHistoryMenu> = fetchOrderHistoryDetail(orderId)
//                val adapter = OrderHistoryRVAdapter(orderHistoryList)
//
//                // RecyclerView Adapter 초기화
//
//                recyclerView.adapter = adapter
//            } catch (e: IOException) {
//                // 네트워크 오류 처리
//                Log.e("YourFragment", "Failed to fetch order history. Exception: ${e.message}", e)
//            }
//        }


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // "뒤로 가기" 버튼에 클릭 리스너 추가
        binding.backBtnIv.setOnClickListener {
            // 이전 프래그먼트로 이동
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private suspend fun fetchOrderHistoryDetail(orderId: Int): List<OrderHistoryDetail> {
        try {
            val response = ApiClient.retrofitInterface.getOrderDetail(orderId)

            if (response.isSuccessful) {
                return response.body() ?: throw Exception("Empty response body")
            } else {
                throw Exception("Failed to fetch order detail. HTTP code: ${response.code()}")
            }
        } catch (e: Exception) {
            throw Exception("Failed to fetch order detail. Exception: ${e.message}", e)
        }
    }
}
