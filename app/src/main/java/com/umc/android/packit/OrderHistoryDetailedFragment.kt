package com.umc.android.packit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
        binding = FragmentOrderHistoryDetailedBinding.inflate(inflater, container, false)

        val rootView = inflater.inflate(R.layout.fragment_order_history_detailed, container, false)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.menu_list_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context)

        val storeTextView =  rootView.findViewById<TextView>(R.id.store_name_tv)
        val pickupTextView = rootView.findViewById<TextView>(R.id.receipt_pick_up_02_tv)
        val priceTextView =rootView.findViewById<TextView>(R.id.receipt_total_price_02_tv)

        val orderId = arguments?.getInt("ORDER_ID", -1) ?: -1

        if (orderId != -1) {
            // Use the orderId to fetch detailed order history
            lifecycleScope.launch {
                try {
                    val orderHistoryList: List<OrderHistoryDetail> = fetchOrderHistoryDetail(orderId)
                    val adapter = OrderHistoryDetailedRVAdapter(orderHistoryList)

                    // Set up RecyclerView Adapter
                    recyclerView.adapter = adapter
                    storeTextView.text=orderHistoryList[0].store_name
                    pickupTextView.text=orderHistoryList[0].pickup_time
                    priceTextView.text=orderHistoryList[0].fee+"원"


                } catch (e: IOException) {
                    // Handle network error
                    Log.e("OrderHistoryDetailedFrag", "Failed to fetch detailed order history. Exception: ${e.message}", e)
                }
            }
        } else {
            // Handle invalid orderId
            Log.e("OrderHistoryDetailedFrag", "Invalid orderId")
        }


        rootView.findViewById<ImageView>(R.id.back_btn_iv).setOnClickListener {
            // Create an instance of OrderHistoryFragment
            val orderHistoryFragment = OrderHistoryFragment()

            // Perform the fragment transaction
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.container, orderHistoryFragment)
                .addToBackStack(null) // Optional, if you want to add to the back stack
                .commit()
        }


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
