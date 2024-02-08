package com.umc.android.packit

data class OrderHistoryMenu(
    val date: String,
    val imageUrl: Int,
    val storeName: String,
    val reservationTime: String,
    val menu: String,
    val price: String,
    val state: String
)
