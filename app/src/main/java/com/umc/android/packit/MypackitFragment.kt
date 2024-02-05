package com.umc.android.packit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MypackitFragment : Fragment() {

    private val dummyItemList = listOf(
        MypackitItem(R.drawable.mypackit_sam, "첫 주문", "2024.2.4"),
        MypackitItem(R.drawable.mypackit_sam, "3회 주문", "2024.2.5"),
        MypackitItem(R.drawable.mypackit_sam, "5회 주문", "2024.2.6")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_mypackit, container, false)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.badgeRecyclerView)

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = MypackitPinAdapter(dummyItemList)

        return rootView
    }

}
