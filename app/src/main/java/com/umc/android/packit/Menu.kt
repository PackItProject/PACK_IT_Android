package com.umc.android.packit

import org.w3c.dom.Text
import java.io.Serializable

data class Menu(
    var id: Int = 0,
    var store_id: Int = 0,
    var menu_name: String = "",
    var about_menu: String = "",
    var containter: String = "",
    var price: Int = 0,
    var insulation: Int =0,
    var liquid_seal: Int=0,
    var notice: String ="",
    var status: Int = 0,
    var menu_category: Int =0,
    var image: String? = null,
    var quantity: Int = 1,
): Serializable

