package com.umc.android.packit

import java.io.Serializable

data class OrderMenu(
   var menu_id: Int = 0,
    var quantity: Int = 0
) : Serializable

