package com.example.packit

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

data class Store(
    var id: Int = 0,
    var name: String? = "",
    var address: String? = "",
    var state: String? = "",
    var rate: String? = "",
    var storeImg: Int? = null,
)