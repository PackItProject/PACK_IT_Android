package com.umc.android.packit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_main)

        val btnKakaoLogin: Button = findViewById(R.id.btnKakaoLogin)

        btnKakaoLogin.setOnClickListener {
            // 카카오 로그인 버튼 클릭 시 MainActivity로 이동
            startActivity(Intent(this, MainActivity::class.java))
            finish() // 현재 액티비티 종료
        }
    }

}