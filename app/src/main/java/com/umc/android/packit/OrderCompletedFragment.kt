

package com.umc.android.packit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.umc.android.packit.databinding.FragmentOrderCompletedBinding
import androidx.fragment.app.FragmentTransaction

// 프래그먼트 상속
class OrderCompletedFragment:Fragment() {
    lateinit var binding:FragmentOrderCompletedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOrderCompletedBinding.inflate(inflater, container, false)
        val view = binding.root

        //주문 상세 버튼 클릭-> 가게 정보 프래그먼트로 페이지 이동
        val orderDetailButton: Button = view.findViewById(R.id.order_completed_order_detailed_btn)
        orderDetailButton.setOnClickListener {
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            val storeinfoFragment = StoreinfoFragment()
            transaction.replace(R.id.fl_basic, storeinfoFragment)
            transaction.commit()
        }

        //닫기 버튼 클릭-> 기본 화면인 맵프래그먼트로 페이지이동
        val closeButton: Button = view.findViewById(R.id.order_completed_closed_btn_iv)
        closeButton.setOnClickListener {
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            val mapFragment = MapFragment()
            transaction.replace(R.id.fl_basic, mapFragment)
            transaction.commit()
        }

        //주문 취소 버튼 클릭-> 주문 취소하시겠습니까? 팝업
        val orderCancelButton: Button = view.findViewById(R.id.order_completed_order_deleted_btn)
        orderCancelButton.setOnClickListener {
            val dialog = OrderCancelDialog()
            dialog.show(requireActivity().supportFragmentManager,"OrderCancelDialog")
        }

        return view
    }


}





