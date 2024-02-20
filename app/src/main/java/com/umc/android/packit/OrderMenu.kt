package com.umc.android.packit


// TODO:수정할 것- 삭제해도 됨

// 주문한 메뉴
// 간단하게 메뉴 이름, 가격, 수량만

data class OrderMenu(
   var menu_id: Int = 0,
    var quantity: Int = 0
)

