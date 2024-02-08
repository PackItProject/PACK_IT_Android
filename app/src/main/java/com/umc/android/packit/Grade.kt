package com.umc.android.packit

import java.io.Serializable

data class Grade(
    var id: Int = 0,
    var store_id: Int = 0,
    var user_name: String = "",
    var grade: Double = 0.0,
    var gradeImg: Int? =null,
    var content: String = "",
    ): Serializable
