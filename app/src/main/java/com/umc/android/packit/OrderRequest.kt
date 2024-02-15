package com.umc.android.packit

//주문추가, 삭제 api 위해 만든 데이터 클래스
data class OrderRequest (
    val pk_user: Int,
    val store_id: Int,
    val requirement: String,
    val payment: Int,
    val pickup_time: String,
    val status: Int,
    val menus: List<Menu>,
    val fee: Int
)