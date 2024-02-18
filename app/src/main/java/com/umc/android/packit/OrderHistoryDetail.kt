package com.umc.android.packit

data class OrderHistoryDetail(
    val store_name: String,
    val pickup_time: String,
    val menu_name: String,
    val price: Int,
    val payment: Int,
    val fee: String,
    val image: String
)
