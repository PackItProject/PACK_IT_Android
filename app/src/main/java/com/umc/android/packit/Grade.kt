package com.umc.android.packit

import java.io.Serializable

data class Grade(
    var store_id: Int = 0,
    var store_name: String = "",
    var nickname: String = "",
    var grade: Double = 0.0,
    var image: String? = null,
    var content: String = "",
    ): Serializable
