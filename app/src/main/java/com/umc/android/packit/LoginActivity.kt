package com.umc.android.packit

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        var keyHash = Utility.getKeyHash(this)
//        Log.d("keyhash: ",keyHash)


        // 로그인 여부 확인
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
                        // 로그인 필요
                        setContentView(R.layout.activity_login_main)
                        initializeLoginButton()
                    } else {
                        // 기타 에러
                        // 로그인 필요
                        setContentView(R.layout.activity_login_main)
                        initializeLoginButton()
                    }
                } else {
                    // 토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        } else {
            // 로그인 필요
            setContentView(R.layout.activity_login_main)
            initializeLoginButton()
        }
    }

    private fun initializeLoginButton() {
        val btnKakaoLogin: Button = findViewById(R.id.btnKakaoLogin)

        btnKakaoLogin.setOnClickListener {
            // 로그인 함수 호출
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")

                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e(TAG, "사용자 정보 요청 실패", error)
                        }
                        else if (user != null) {
                            Log.i(TAG, "사용자 정보 요청 성공" +
                                    "\n회원번호: ${user.id}" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")

                            lifecycleScope.launch {
                                try {
                                    // 로그인한 사용자 정보를 서버로 전송
                                    val userInfo =
                                        user.kakaoAccount?.profile?.thumbnailImageUrl?.let { it1 ->
                                            user.kakaoAccount!!.email?.let { it2 ->
                                                user.kakaoAccount?.profile?.nickname?.let { it3 ->
                                                    LoginRequest(
                                                        kakaoId = user.id!!.toInt(),
                                                        email = it2,
                                                        name = it3,
                                                        profileImage = it1
                                                    )
                                                }
                                            }
                                        }

                                    val response = userInfo?.let {
                                        ApiClient.retrofitInterface.sendUserInfo(userInfo)
                                    }

                                    val bundle = Bundle().apply {
                                        putString("userEmail", user.kakaoAccount?.email)
                                    }

                                    val myInfoFragment = MyInfoFragment().apply {
                                        arguments = bundle
                                    }

                                    if (response!!.isSuccessful) {
                                        // 성공적으로 전송됨
                                        Log.i(TAG, "사용자 정보 전송 성공")

                                        val sharedPreferencesManager = SharedPreferencesManager(this@LoginActivity)
                                        sharedPreferencesManager.saveUserId(user.kakaoAccount?.email!!)



                                    } else {
                                        // 서버에서 오류 응답
                                        Log.e(TAG, "사용자 정보 전송 실패. HTTP code: ${response.code()}")
                                    }
                                } catch (e: Exception) {
                                    // 예외 처리
                                    Log.e(TAG, "사용자 정보 전송 실패", e)
                                }
                            }



                        }
                    }



                    // 로그인 성공 시 LoginConsentActivity로 이동하고 현재 액티비티를 종료
                    val intent = Intent(this@LoginActivity, LoginConsentActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }



}