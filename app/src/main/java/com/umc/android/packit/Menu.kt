package com.umc.android.packit

data class Menu(
    var id: Int = 0,
    var store_id: Int = 0,
    var menu_name: String? = "",
    var containers: String? = "",
    var price: Int = 0,
    var category: Int =0,
    var menuImg: Int? =null,
)
