package com.umc.android.packit

import android.Manifest
import android.R.attr.name
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.umc.android.packit.databinding.ActivityProfileBinding
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


class ProfileActivity : ProfilePermissionActivity() {
    // 변수 선언
    private lateinit var binding: ActivityProfileBinding

    val PERM_GALLERY = 1    // 갤러리 접근 권한 코드
    val maxLength = 12      // editText 글자 수 제한

    lateinit var profileData : Profile  // profile 데이터 클래스

    override fun onCreate(savedInstanceState: Bundle?) {
        // xml 바인딩 작업
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
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
        binding.profileNicknameEt.filters = arrayOf(InputFilter.LengthFilter(maxLength))

        // 확인 버튼 클릭 시, 닉네임 유효성 검증
        binding.profileConfirmBtn.setOnClickListener {
            checkNicknameValidity()
        }

        // 프로필 사진 버튼
        binding.profilePhotoIv.setOnClickListener {
            // 갤러리 권한 요청
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
                requirePermission(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERM_GALLERY)
            else requirePermission(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), PERM_GALLERY)
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
            binding.profileConfirmBtn.setBackgroundResource(R.drawable.btn_square_main)
            binding.profileConfirmBtn.setTextColor(ContextCompat.getColor(this,R.color.white))
        } else {
            // editText가 비어 있는 경우, 원래 색상으로 변경
            binding.profileConfirmBtn.setBackgroundResource(R.drawable.btn_square_enable)
            binding.profileConfirmBtn.setTextColor(ContextCompat.getColor(this,R.color.grey))
        }
    }

    // 닉네임 검증 에러 처리
    private fun checkNicknameValidity() {
        // editText의 내용을 가져옴
        val nickname = binding.profileNicknameEt.text.toString()
        // imageView의 내용을 가져옴
        val drawable = binding.profileUserIv.drawable
        val profileImage: Bitmap? = if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else {
            null // 형변환이 불가능한 경우에 대한 처리
        }

        if (isValidNickname(nickname)) {
            // 데이터 클래스와 내용 연결
            profileData = Profile(
                nickname = nickname,
                profile = profileImage
            )

            // TODO: 확인용 -> 지울거임
            // Toast로 데이터 클래스의 내용 및 이미지 확인
            val toastMessage = "Nickname: ${profileData.nickname}"

// Bitmap을 Base64 문자열로 인코딩
            val byteArrayOutputStream = ByteArrayOutputStream()
            profileData.profile?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT)

// Base64 문자열을 Toast 메시지에 추가
            val fullToastMessage = "$toastMessage\nProfile Image: $base64Image"

            Toast.makeText(this, fullToastMessage, Toast.LENGTH_SHORT).show()

            // 유효한 경우, 페이지 이동 -> 메인 액티비티
            val intent = Intent(this, MainActivity::class.java)

            // 플래그 설정 (지금까지의 액티비티 초기화)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
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

    // 권한 요청 허용
    override fun grantPermission(requestCode: Int) {
        when(requestCode){
            PERM_GALLERY -> openGallery()
        }
    }

    // 권한 요청 거부
    override fun denyPermission(requestCode: Int) {
        when(requestCode){
            PERM_GALLERY ->
                Toast.makeText(this, "갤러리 접근 권한을 승인해야 합니다", Toast.LENGTH_SHORT).show()
        }
    }

    // 갤러리 열기 (접근 성공)
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE // 이미지만을 선택
        startActivityForResult(intent, PERM_GALLERY)
    }

    // 갤러리에서 이미지 가져오기
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PERM_GALLERY -> {
                    // 이미지 주소를 그냥 가져옴
                    data?.data?.let { uri ->
                        // 비트맵으로 전환
                        //val bitmap: Bitmap? = uriToBitmap(uri)

                        // Set the Bitmap to the ImageView
                        //bitmap?.let {
                            //binding.profileUserIv.setImageBitmap(it)
                        //}
                    }
                }
            }
        }
    }


}