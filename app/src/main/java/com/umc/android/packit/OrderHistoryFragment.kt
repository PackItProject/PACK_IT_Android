package com.umc.android.packit

import com.umc.android.packit.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc.android.packit.databinding.FragmentOrderHistoryBinding


class OrderHistoryFragment : Fragment() {

    lateinit var binding: FragmentOrderHistoryBinding
    private var view: View? = null
    private var detailBtn: Button? = null //StoreInfoFragment로 이동하는 버튼

    val dummyList: List<OrderHistoryMenu> = listOf(
        OrderHistoryMenu(
            date = "2024.01.01 00:00",
            imageUrl = R.drawable.store_img_1,
            storeName = "가게 이름 1",
            reservationTime = "예약 시간 1",
            menu = "메뉴 1",
            price = "10000원",
            state = "주문 완료"
        ),
        OrderHistoryMenu(
            date = "2024.02.01 12:00",
            imageUrl = R.drawable.store_img_2,
            storeName = "가게 이름 2",
            reservationTime = "예약 시간 2",
            menu = "메뉴 2",
            price = "15000원",
            state = "주문 취소"
        ),
        OrderHistoryMenu(
            date = "2024.03.01 18:00",
            imageUrl =  R.drawable.store_img_3,
            storeName = "가게 이름 3",
            reservationTime = "예약 시간 3",
            menu = "메뉴 3",
            price = "20000원",
            state = "주문 완료"
        ),
        // 이하 추가 데이터 추가 가능
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)

        val rootView = inflater.inflate(R.layout.fragment_order_history, container, false)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.history_main_recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = OrderHistoryRVAdapter(dummyList)

        return rootView


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
//        binding.historyMainRecyclerView.layoutManager = LinearLayoutManager(requireContext())

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
//        return binding.root
    }


}
