package com.umc.android.packit

import com.google.gson.annotations.SerializedName

data class DeleteCartRequest(
    @SerializedName("pk_user") var pk_user: Int,
    @SerializedName("store_id") var store_id: Int,
    @SerializedName("menu_id") var menu_id: Int
)
