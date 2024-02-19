package com.umc.android.packit

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "pk_user", // 여기에 원하는 이름을 지정
        Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_USER_ID = "pk_user"
    }

    // userId 저장
    fun saveUserId(pkUser: Int) {
        sharedPreferences.edit().putInt(KEY_USER_ID, pkUser).apply()
    }

    // 저장된 userId 불러오기
    fun getUserId(): Int {
        return sharedPreferences.getInt(KEY_USER_ID, -1) // 기본값은 -1로 설정하거나 다른 값으로 지정
    }

    // 필요에 따라 다른 데이터를 저장하고 불러오는 함수들을 추가할 수 있음
}
