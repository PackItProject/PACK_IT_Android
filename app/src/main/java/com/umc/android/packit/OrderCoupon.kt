package com.umc.android.packit

// 쿠폰 데이터 구현 미완
// TODO: 쿠폰 데이터 수정
data class OrderCoupon(
    var name: String? = "", //쿠폰 이름
    var discountPercent:Double?=0.0 //할인율
)