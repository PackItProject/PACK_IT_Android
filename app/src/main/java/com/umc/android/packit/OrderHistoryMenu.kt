package com.umc.android.packit

import com.google.gson.annotations.SerializedName

//data class OrderHistoryMenu(
//    val date: String,
//    val imageUrl: Int,
//    val storeName: String,
//    val reservationTime: String,
//    val menu: String,
//    val price: String,
//    val state: String
//)


data class OrderHistoryMenu(
    @SerializedName("id") val orderId: Int,
    @SerializedName("created_at") val date: String,
    @SerializedName("store_name") val storeName: String,
    @SerializedName("pickup_time") val reservationTime: String,
    @SerializedName("quantity") val menu: Int
)


