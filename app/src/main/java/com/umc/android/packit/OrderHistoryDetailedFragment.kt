package com.umc.android.packit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.umc.android.packit.databinding.FragmentOrderBinding
import com.umc.android.packit.databinding.FragmentOrderHistoryDetailedBinding

class OrderHistoryDetailedFragment: Fragment() {
    private lateinit var binding: FragmentOrderHistoryDetailedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderHistoryDetailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // "뒤로 가기" 버튼에 클릭 리스너 추가
        binding.backBtnIv.setOnClickListener {
            // 이전 프래그먼트로 이동
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
