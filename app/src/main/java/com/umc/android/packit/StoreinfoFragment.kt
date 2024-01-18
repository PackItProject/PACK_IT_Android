package com.umc.android.packit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.android.packit.databinding.FragmentStoreinfoBinding

class StoreinfoFragment : Fragment() {

    lateinit var binding: FragmentStoreinfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreinfoBinding.inflate(inflater,container,false)

        return binding.root
    }
}