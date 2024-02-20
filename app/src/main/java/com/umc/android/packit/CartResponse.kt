package com.umc.android.packit

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CartResponse(
    // 유저 아이디
    @SerializedName("pk_user") val pk_user: Int,

    // 가게 아이디
    @SerializedName("store_id") val store_id: Int,

    // 가게 이름
    @SerializedName("store_name") val store_name: String,

    // 메뉴 아이디
    @SerializedName("menu_id") val menu_id: Int,

    // 메뉴 이름
    @SerializedName("menu_name") val menu_name: String,

    // 수량
    @SerializedName("quantity") var count: Int,

    // 메뉴 가격
    @SerializedName("price") val price: Int,

    // 메뉴 이미지
    @SerializedName("image") val image: String?,


    // 필요 없는 속성
    val name: String,
    val phone_number: String,
    val address: String,
    val notice: String
)
