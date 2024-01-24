package com.umc.android.packit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class AccumulationFragment : Fragment() {

//    private lateinit var viewModel: AccumulationViewModel
//
//    private lateinit var textViewAccumulation: TextView
//    private lateinit var imageViewAccumulation: ImageView
//    private lateinit var imageViewStamp: ImageView
//    private lateinit var imageViewBadge: ImageView
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_mypackit, container, false)
//
//        textViewAccumulation = view.findViewById(R.id.textViewragment_mypackit)
//        imageViewAccumulation = view.findViewById(R.id.imageViewragment_mypackit)
//        imageViewStamp = view.findViewById(R.id.imageViewStamp)
//        imageViewBadge = view.findViewById(R.id.imageViewBadge)
//
//        return view
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // ViewModel 초기화
//        viewModel = ViewModelProvider(requireActivity()).get(AccumulationViewModel::class.java)
//
//        // LiveData를 관찰하여 데이터 업데이트
//        viewModel.accumulationPoints.observe(viewLifecycleOwner, { accumulationPoints ->
//            updateUI(accumulationPoints)
//        })
//    }
//
//    private fun updateUI(accumulationPoints: Int) {
//        // UI 업데이트
//        textViewAccumulation.text = "적립 내역: $accumulationPoints 점"
//
//        // 이미지 및 다른 UI 업데이트 로직도 추가할 수 있음
//    }
}
