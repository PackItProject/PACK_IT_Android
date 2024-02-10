package com.umc.android.packit

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.umc.android.packit.databinding.ActivityLoginConsentBinding


class LoginConsentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginConsentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginConsentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 각 체크 박스 클릭 시, 체크 효과 및 확인 버튼 배경 변경
        binding.horizontalAlign01.setOnClickListener {
            changeCheckOnImage(binding.checkOffBtn01Iv, binding.checkOnBtn01Iv,
                binding.checkErrorBtn01Iv, binding.agree01Tv)
            changeButtonBackground()
        }

        binding.horizontalAlign02.setOnClickListener {
            changeCheckOnImage(binding.checkOffBtn02Iv, binding.checkOnBtn02Iv,
                binding.checkErrorBtn02Iv, binding.agree02Tv)
            changeButtonBackground()
        }

        binding.horizontalAlign03.setOnClickListener {
            changeCheckOnImage(binding.checkOffBtn03Iv, binding.checkOnBtn03Iv,
                binding.checkErrorBtn03Iv, binding.agree03Tv)
            changeButtonBackground()
        }

        // 확인 버튼 클릭 시, 체크박스 유효성 검증
        binding.confirmBtn.setOnClickListener {
            checkCheckBoxValidity()
        }
    }

    // 체크 박스 이미지를 전환하는 함수
    private fun changeCheckOnImage(checkOff: ImageView, checkOn: ImageView, checkError: ImageView, checkText: TextView) {
        // 기존의 상태에 따라 다른 이미지 표시
        if (checkOff.visibility == View.VISIBLE || checkError.visibility == View.VISIBLE) {
            // Off 또는 Error 상태일 때 클릭하면 On으로 변경
            checkOff.visibility = View.GONE
            checkOn.visibility = View.VISIBLE
            checkError.visibility = View.GONE
        } else if (checkOn.visibility == View.VISIBLE) {
            // On 상태일 때 클릭하면 Off로 변경
            checkOff.visibility = View.VISIBLE
            checkOn.visibility = View.GONE
            checkError.visibility = View.GONE
        }
        // 에러 메시지 숨기기
        binding.errorMessageTv.visibility = View.GONE
        // 텍스트 색 기본으로 변경
        checkText.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
    }

    // 체크 박스 이미지를 전환하는 함수
    private fun changeCheckErrorImage(checkOff: ImageView, checkOn: ImageView, checkError: ImageView, checkText: TextView) {
        // Off 상태일 때 클릭하면 Error 상태로 변경
        checkOff.visibility = View.GONE
        checkOn.visibility = View.GONE
        checkError.visibility = View.VISIBLE

        // 에러 메시지 숨기기
        binding.errorMessageTv.visibility = View.VISIBLE
        checkText.setTextColor(ContextCompat.getColor(this, R.color.red))
    }

    // 확인 버튼 배경 바꾸기
    private fun changeButtonBackground() {
        val isCheckBox01Checked = binding.checkOnBtn01Iv.visibility == View.VISIBLE
        val isCheckBox02Checked = binding.checkOnBtn02Iv.visibility == View.VISIBLE
        val isCheckBox03Checked = binding.checkOnBtn03Iv.visibility == View.VISIBLE

        if (isCheckBox01Checked && isCheckBox02Checked && isCheckBox03Checked) {
            // 모든 체크박스가 체크된 상태일 때 배경 및 텍스트 색상 변경
            binding.confirmBtn.setBackgroundResource(R.drawable.btn_square)
            binding.confirmBtn.setTextColor(ContextCompat.getColor(this, R.color.white))
        } else {
            // 하나라도 체크되지 않은 경우 기본 색상으로 변경
            binding.confirmBtn.setBackgroundResource(R.drawable.btn_square_border)
            binding.confirmBtn.setTextColor(ContextCompat.getColor(this, R.color.grey))
        }
    }

    // 체크박스 유효성 검증
    private fun checkCheckBoxValidity() {
        val isCheckBox01Checked = binding.checkOnBtn01Iv.visibility == View.VISIBLE
        val isCheckBox02Checked = binding.checkOnBtn02Iv.visibility == View.VISIBLE
        val isCheckBox03Checked = binding.checkOnBtn03Iv.visibility == View.VISIBLE

        if (isCheckBox01Checked && isCheckBox02Checked && isCheckBox03Checked) {
            // 화면 이동 -> 프로필 액티비티
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        } else {
            // 하나라도 체크되지 않은 경우 에러 문구 띄우기
            binding.errorMessageTv.visibility = View.VISIBLE

            // 체크되지 않은 체크박스의 텍스트 색상을 빨간색으로 변경
            if (!isCheckBox01Checked) {
                changeCheckErrorImage(binding.checkOffBtn01Iv, binding.checkOnBtn01Iv,
                    binding.checkErrorBtn01Iv, binding.agree01Tv)
            }
            if (!isCheckBox02Checked) {
                changeCheckErrorImage(binding.checkOffBtn02Iv, binding.checkOnBtn02Iv,
                    binding.checkErrorBtn02Iv, binding.agree02Tv)
            }
            if (!isCheckBox03Checked) {
                changeCheckErrorImage(binding.checkOffBtn03Iv, binding.checkOnBtn03Iv,
                    binding.checkErrorBtn03Iv, binding.agree03Tv)
            }
        }
    }
}