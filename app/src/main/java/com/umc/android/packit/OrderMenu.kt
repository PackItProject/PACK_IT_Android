package com.umc.android.packit


// TODO:수정할 것

// 주문한 메뉴
// 간단하게 메뉴 이름, 가격, 수량만

data class OrderMenu(
    var menu_name: String? = "",
    var price: Int = 0,
    var count: Int = 1,
)
