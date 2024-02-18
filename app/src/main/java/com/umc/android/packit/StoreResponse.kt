package com.umc.android.packit

import com.google.gson.annotations.SerializedName

// 가게 목록 조회 데이터 클래스
data class StoreResponse(
    // 가게 아이디
    @SerializedName("store_id")
    val store_id: Int?,

    // 가게 이름
    @SerializedName("store_name")
    val store_name: String,

    // 가게 상태
    @SerializedName("status")
    val status: Int,

    // 가게 평점
    @SerializedName("grade")
    val average_grade: Double,

    // TODO: 가게 북마크 추가 예정
    val is_bookmarked: Int,

    // 가게 주소
    @SerializedName("address")
    val address: String,

    // 가게 이미지
    @SerializedName("image")
    val image: String?,

    // 유저 아이디
    @SerializedName("pk_user")
    val pk_user: Int?
)
