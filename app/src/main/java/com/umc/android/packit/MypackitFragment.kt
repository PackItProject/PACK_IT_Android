package com.umc.android.packit

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc.android.packit.databinding.FragmentMypackitBinding

class MypackitFragment : Fragment() {
    private lateinit var binding: FragmentMypackitBinding

    // 전역 변수로 10개의 Boolean 값을 담는 리스트 변수 선언
    private var stampList: MutableList<Boolean> = MutableList(10) { false }

    private var BadgeList = listOf(
        MypackitItem(R.drawable.img_badge_1st, "단 1개 뿐인 지구를 위한 1번째 걸음", "2024.2.21"),
        MypackitItem(R.drawable.img_badge_5th, "누적 주문 5회 주문", "2024.2.21"),
        MypackitItem(R.drawable.img_badge_10th, "누적 주문 10회", "2024.2.21"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypackitBinding.inflate(inflater, container, false)
        val rootView = binding.root

        if (binding != null) {
            // 리사이클러뷰 설정
            val recyclerView: RecyclerView = binding.root.findViewById(R.id.badgeRecyclerView)
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)


            // SharedPreference에서 닉네임을 불러와서 name_tv에 표시
            val sharedPreference = requireActivity().getSharedPreferences("sp1", Context.MODE_PRIVATE)
            val nickname = sharedPreference.getString("name", "데이터 없음")
            binding.nameTv.text = nickname

            //TODO:SharedPreference에서 주문횟수를 불러와서
            val orderCountValue = sharedPreference.getInt("orderCount",0) //숫자를 문자로 바꾸어 쉐어드프리퍼런스에 저장
            binding.orderCountTv.text = orderCountValue.toString()+"회"

            // BadgeList 업데이트: 주문 횟수만큼 배지 가져옴 + 역순으로 넣을 준비
            BadgeList = when {
                orderCountValue == 0 -> emptyList()
                orderCountValue in 1..4 -> listOf(BadgeList[0])
                orderCountValue in 5..9 -> BadgeList.take(2).reversed()
                else -> BadgeList.take(3).reversed()
            }
            // 리사이클러뷰 연결
            recyclerView.adapter = MypackitPinAdapter(BadgeList)

            // stampList 업데이트: 주문 횟수만큼 도장 찍을 준비
            // 11개 이상이면 나머지 취급
            val stampCount = if (orderCountValue >= 11) orderCountValue % 10 else orderCountValue
            for (i in 0 until stampCount) {
                stampList[i] = true
            }

            // stampList가 true면 도장 이미지 업데이트
            for ((index, value) in stampList.withIndex()) {
                val imageViewId = when (index) {
                    0 -> R.id.stamp1
                    1 -> R.id.stamp2
                    2 -> R.id.stamp3
                    3 -> R.id.stamp4
                    4 -> R.id.stamp5
                    5 -> R.id.stamp6
                    6 -> R.id.stamp7
                    7 -> R.id.stamp8
                    8 -> R.id.stamp9
                    9 -> R.id.stamp10
                    else -> null
                }

                imageViewId?.let { imageViewId ->
                    val imageView = binding.root.findViewById<ImageView>(imageViewId)

                    if (value) {
                        // true일 때 도장 이미지
                        imageView.setImageResource(R.drawable.mypack_stamp_yes_gui)
                    } else {
                        // false일 때 빈 도장 이미지
                        imageView.setImageResource(R.drawable.mypackit_stamp_no_gui)
                    }
                }
            }
        }

        return rootView
    }

    fun changeTextView(string: String){
        binding.nameTv.text = string
    }

}
