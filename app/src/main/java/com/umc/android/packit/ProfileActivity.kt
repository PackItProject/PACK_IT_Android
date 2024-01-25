package com.umc.android.packit

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.umc.android.packit.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    // 변수 선언
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // xml 바인딩 작업
        binding = ActivityProfileBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // editText 내용에 따른 이벤트 처리
        binding.profileNicknameEt.addTextChangedListener(object : TextWatcher{
            // 사용자가 입력한 내용이 변경된 후에 호출되는 메서드
            override fun afterTextChanged(s: Editable?) {
                // editText 효과 초기화
                initEditText()
                // editText의 내용 유무에 따라 button의 배경색을 바꿈
                changeButtonBackground(!s.isNullOrEmpty())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 사용자가 입력한 내용이 변경되기 전에 호출되는 메서드
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 사용자가 입력한 내용이 변경되고 있는 동안에 호출되는 메서드
            }
        })

        // editText 글자 수 제한 추가 (최대 12글자)
        val maxLength = 12
        binding.profileNicknameEt.filters = arrayOf(InputFilter.LengthFilter(maxLength))

        // 확인 버튼 클릭 시, 닉네임 유효성 검증
        binding.profileConfirmBtn.setOnClickListener {
            checkNicknameValidity()
        }
    }

    // editText 효과 초기화 함수
    private fun initEditText() {
        // 에러 메시지 감춤
        binding.profileErrorMessageTv.visibility = View.INVISIBLE
        // editText 테두리 복구 (빨강 -> 회색)
        binding.profileNicknameEt.setBackgroundResource(R.drawable.et_valid)
    }

    // button 효과 함수 (editText의 내용 유무에 따라 button의 배경색을 바꿈)
    private fun changeButtonBackground(isTyping: Boolean) {
        if (isTyping) {
            // editText가 비어 있지 않은 경우, 버튼 및 텍스트 색 변경
            binding.profileConfirmBtn.setBackgroundResource(R.drawable.btn_square)
            binding.profileConfirmBtn.setTextColor(ContextCompat.getColor(this,R.color.white));
        } else {
            // editText가 비어 있는 경우, 원래 색상으로 변경
            binding.profileConfirmBtn.setBackgroundResource(R.drawable.btn_square_border)
                binding.profileConfirmBtn.setTextColor(ContextCompat.getColor(this,R.color.grey))
        }
    }

    // 닉네임 유효성 검증 함수
    private fun checkNicknameValidity() {
        // editText의 내용을 가져옴
        val nickname = binding.profileNicknameEt.text.toString()

        if (isValidNickname(nickname)) {
            // 유효한 경우, 에러 메시지 감춤
            binding.profileErrorMessageTv.visibility = View.INVISIBLE
        } else {
            // 유효하지 않은 경우, 에러 메시지 띄우고 테두리 색 (회색 -> 빨강) 변경
            binding.profileErrorMessageTv.visibility = View.VISIBLE
            binding.profileNicknameEt.setBackgroundResource(R.drawable.et_invalid)
        }
    }

    // 닉네임 유효성 검증 함수
    private fun isValidNickname(nickname: String): Boolean {
        // 닉네임과 정규표현식(영어, 숫자, 한글)과의 매치작업
        val regex = Regex("[a-zA-Z0-9가-힣]+")
        return nickname.matches(regex)
    }
}