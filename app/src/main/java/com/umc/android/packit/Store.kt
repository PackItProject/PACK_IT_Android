package com.umc.android.packit

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "StoreTable")
data class Store(
    @PrimaryKey(autoGenerate = false) var id: Int = 0,
    var name: String? = "",
    var address: String? = "",
    var state: String? = "",
    var rate: String? = "",
    var storeImg: Int? = null,
)