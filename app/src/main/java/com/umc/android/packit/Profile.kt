package com.umc.android.packit

import android.graphics.Bitmap

// ERD 대로 만들어 둠
data class Profile(
    var pk_user : Int = 0,  // pk
    var nickname : String = "", // 이름 -> 별명
    var email : String = "",// 메일
    var profile : Bitmap? = null  // 프로필 사진
)