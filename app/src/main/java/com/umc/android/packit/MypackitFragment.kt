package com.umc.android.packit

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc.android.packit.databinding.FragmentMypackitBinding

class MypackitFragment : Fragment() {
    private lateinit var binding: FragmentMypackitBinding

    private val dummyItemList = listOf(
        MypackitItem(R.drawable.mypack_badge, "누적 주문 5회", "2024.2.4"),
        MypackitItem(R.drawable.mypack_badge, "3회 주문", "2024.1.15"),
        MypackitItem(R.drawable.mypack_badge, "단 1개 뿐인 지구를 위한 1번째 걸음", "2024.1.1")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypackitBinding.inflate(inflater, container, false)
        val rootView = binding.root

        if (binding != null) {
            val recyclerView: RecyclerView = binding.root.findViewById(R.id.badgeRecyclerView)

            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerView.adapter = MypackitPinAdapter(dummyItemList)

            // SharedPreference에서 닉네임을 불러와서 name_tv에 표시
            val sharedPreference = requireActivity().getSharedPreferences("sp1", Context.MODE_PRIVATE)
            val nickname = sharedPreference.getString("name", "데이터 없음")
            binding.nameTv.text = nickname

            //TODO:SharedPreference에서 주문횟수를 불러와서
            val orderCountValue = sharedPreference.getInt("orderCount",0) //숫자를 문자로 바꾸어 쉐어드프리퍼런스에 저장
            binding.orderCountTv.text = orderCountValue.toString()+"회"

            if (orderCountValue != null) {
                Log.d("0220get",orderCountValue.toString())
            }
        }

        return rootView
    }

    fun changeTextView(string: String){
        binding.nameTv.text = string
    }

}
