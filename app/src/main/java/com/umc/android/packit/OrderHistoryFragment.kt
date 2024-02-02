package com.umc.android.packit

import com.umc.android.packit.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.android.packit.databinding.FragmentOrderHistoryBinding


class OrderHistoryFragment : Fragment() {

    lateinit var binding: FragmentOrderHistoryBinding
    private var view: View? = null
    private var detailBtn: Button? = null //StoreInfoFragment로 이동하는 버튼

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

      /*  // 상세 버튼 클릭 시 StoreinfoFragment로 넘어가게 만들기
      //아직 어댑터가 없길래 주석처리해놨습니다
        view = inflater.inflate(R.layout.fragment_order_history, container, false)
        detailBtn = view?.findViewById(R.id.item_order_history_btn)
        detailBtn?.setOnClickListener {
            val transaction: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            // main_layout에 StoreinfoFragment로 transaction 한다.
            transaction.replace(R.id.main_layout, StoreinfoFragment())
            // 꼭 commit을 해줘야 바뀐다.
            transaction.commit()
        }
*/
        return binding.root
    }


}
