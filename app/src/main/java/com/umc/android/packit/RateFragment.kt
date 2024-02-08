package com.umc.android.packit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.android.packit.databinding.FragmentRateBinding

class RateFragment : Fragment(), RateRVAdapter.MyItemClickListener {

    lateinit var binding: FragmentRateBinding
    private var gradeDatas = ArrayList<Grade>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRateBinding.inflate(inflater,container,false)

        val storeId = arguments?.getInt("storeId", -1) ?: -1

        gradeDatas.apply{
            add(Grade(1, storeId, "사용자 1", 4.5,R.drawable.store_img_1, "지난번에 주문했던 메뉴 A가 정말 맛있었어요. 다음에 또 주문해보고 싶습니다."))
            add(Grade(2, storeId, "사용자 2", 3.0,R.drawable.store_img_2, "예상했던 것보다 간이 너무 센게 아쉬워요. 사이드로 주문한 메뉴 B는 입맛에 잘 맞았습니다."))
            add(Grade(3, storeId, "사용자 3", 3.5,R.drawable.store_img_3,"친구랑 같이 잘 먹었습니다! 그런데 고기가 너무 질긴 것 같아요."))
            add(Grade(4, storeId, "사용자 4", 4.1 ,R.drawable.store_img_1, "친구랑 같이 잘 먹었습니다! 그런데 고기가 너무\n" +
                    "질긴 것 같아요."))
            add(Grade(5, storeId, "사용자 1", 4.5,R.drawable.store_img_1, "지난번에 주문했던 메뉴 A가 정말 맛있었어요. 다음에 또 주문해보고 싶습니다."))
            add(Grade(6, storeId, "사용자 2", 3.0,R.drawable.store_img_2, "예상했던 것보다 간이 너무 센게 아쉬워요. 사이드로 주문한 메뉴 B는 입맛에 잘 맞았습니다."))

        }

        val rateRVAdapter = RateRVAdapter(gradeDatas)
        rateRVAdapter.setMyItemClickListener(this)
        binding.rateRecyclerview.adapter = rateRVAdapter

        return binding.root
    }

    companion object {
        fun newInstance(): RateFragment { //MyInfoFragment에서 화면 이동 목적
            return RateFragment()
        }
    }
}