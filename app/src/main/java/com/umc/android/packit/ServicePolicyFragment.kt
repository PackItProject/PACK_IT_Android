package com.umc.android.packit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class ServicePolicyFragment : Fragment() {
    companion object {
        fun newInstance(): ServicePolicyFragment { //MyInfoFragment에서 화면 이동 목적
            return ServicePolicyFragment()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service_policy, container, false)
    }


}