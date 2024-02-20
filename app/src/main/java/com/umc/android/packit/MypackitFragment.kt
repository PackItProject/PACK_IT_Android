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

            // 주문 횟수에 따라 booleanList 업데이트
            // 11개 이상이면 나머지 취급
            val stampCount = if (orderCountValue >= 11) orderCountValue % 10 else orderCountValue

            for (i in 0 until stampCount) {
                stampList[i] = true
            }

            // booleanList를 사용하여 도장 이미지 업데이트
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
                        // true일 때 도장 이미지 설정
                        imageView.setImageResource(R.drawable.mypack_stamp_yes_gui)
                    } else {
                        // false일 때 다른 이미지로 설정
                        // 여기에서 다른 이미지의 리소스 ID를 설정해주세요.
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
