package com.example.packit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.packit.databinding.FragmentOrderHistoryBinding

class OrderHistoryFragment : Fragment() {

    lateinit var binding: FragmentOrderHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)

//        // 주문 내역 데이터 설정 (가정: OrderHistoryItem 모델 사용)
//        val orderHistoryList = listOf(
//            OrderHistoryItem("주문 내역 1"),
//            OrderHistoryItem("주문 내역 2"),
//            OrderHistoryItem("주문 내역 3")
//            // TODO: 실제 데이터로 대체
//        )
//
//        // 어댑터 설정
//        val orderHistoryAdapter = OrderHistoryAdapter(orderHistoryList)
//        binding.historyMainRecyclerView.adapter = orderHistoryAdapter

        // 레이아웃 매니저 설정
        binding.historyMainRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }
}
