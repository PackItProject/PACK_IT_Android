package com.umc.android.packit

data class LoginRequest(
    val kakaoId: Int,
    val email: String,
    val name: String,
    val profileImage: String
)

data class SignInResponse(
    val message: String
    // 정상적인 응답에 대한 필드 정의
    // 예: val accessToken: String
)
