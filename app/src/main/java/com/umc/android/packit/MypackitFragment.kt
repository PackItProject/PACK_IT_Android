package com.umc.android.packit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MypackitFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_mypackit, container, false)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.badgeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = MypackitPinAdapter(getSampleData()) // Implement getSampleData() to provide your data
        recyclerView.adapter = adapter

        return rootView
    }

    private fun getSampleData(): List<String> {
        // Replace this with your actual data
        return listOf("Item 0", "Item 1", "Item 2")
    }
}
