package com.umc.android.packit

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//일단 만들어둠
data class Profile(
    var profileImg: Int? = null, //프로필 사진
    var name: String? = "", //고객명
    var email: String? = "", //이메일 주소
    var homeAddress: String? = "", //집 주소
    var phoneNumber: String? = "", //고객 전화번호
)