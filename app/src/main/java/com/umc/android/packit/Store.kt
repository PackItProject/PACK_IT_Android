package com.umc.android.packit

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
data class Store(
    var store_id: Int = 0,
    var store_name: String? = "",
    var address: String? = "",
    var status: Int = 0,
    var average_grade: Double = 0.0,
    var star: Boolean? = true,
    var storeImg: Int? = null,
)