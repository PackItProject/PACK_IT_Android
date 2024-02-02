package com.umc.android.packit

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
data class Store(
    var id: Int = 0,
    var name: String? = "",
    var address: String? = "",
    var state: String? = "",
    var rate: String? = "",
    var star: Boolean? = false,
    var storeImg: Int? = null,
)