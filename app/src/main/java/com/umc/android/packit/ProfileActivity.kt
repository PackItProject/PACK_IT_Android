package com.umc.android.packit

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.graphics.Bitmap
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
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.umc.android.packit.databinding.ActivityProfileBinding
import java.io.ByteArrayOutputStream



class ProfileActivity : ProfilePermissionActivity() {
    // 변수 선언
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageRefrence: StorageReference
    private lateinit var imageUri:Uri

    //닉네임전달용
    private lateinit var sharedPreferences: SharedPreferences

    val PERM_GALLERY = 1    // 갤러리 접근 권한 코드
    val maxLength = 12      // editText 글자 수 제한

    lateinit var profileData : Profile  // profile 데이터 클래스


    override fun onCreate(savedInstanceState: Bundle?) {
        // xml 바인딩 작업
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //파이어베이스에 프로필 사진 넣기
        auth=FirebaseAuth.getInstance()
        val uid=auth.currentUser?.uid
        databaseReference=FirebaseDatabase.getInstance().getReference("Users")

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

            //TODO: 프로필 이미지와 닉네임 저장
            val nickname=binding.profileNicknameEt.text.toString()
            val bitmap = (binding.profileUserIv.drawable as BitmapDrawable).bitmap

            val user=Profile(0,nickname,"",bitmap)

            if (uid!=null){
                databaseReference.child(uid).setValue(user).addOnCompleteListener {
                    if(it.isSuccessful) {
                        uploadProfilePic()
                    }else{
                        Toast.makeText(this@ProfileActivity,"Failed to update profile",Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        // 프로필 사진 버튼
        binding.profilePhotoIv.setOnClickListener {
            // 갤러리 권한 요청
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
                requirePermission(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERM_GALLERY)
            else requirePermission(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), PERM_GALLERY)
        }
        makeBitmap()
/*

        // 닉네임을 입력받아 SharedPreferences에 저장
        val nickname = binding.profileNicknameEt.text.toString()  // 실제 사용자가 입력한 닉네임을 받아오는 코드
        val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("nickname", nickname)
        editor.apply()
*/


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
                        binding.profileUserIv.setImageURI(uri)



                    }
                }
            }
        }
    }

    //비트맵으로 프로필 사진 저장
    // 비트맵으로 프로필 사진 저장
    private fun makeBitmap() {
        val myInfoFragment = MyInfoFragment()
        val bundle = Bundle()
        // 이미지뷰의 이미지를 비트맵으로 변환
        val bitmap = (binding.profileUserIv.drawable as BitmapDrawable).bitmap

        // 비트맵을 ByteArray로 직렬화
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        // bundle에 데이터 담기
        bundle.putByteArray("img", byteArray)
        bundle.putString("nickname", binding.profileNicknameEt.text.toString())

        myInfoFragment.arguments = bundle
        //Todo: 바로 transaction.commit()하지 못하니 안보이는건가?
        //Todo:외부저장소를 사용해야하나?

        // Assuming you have a FragmentManager
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.commit()
    }


    private fun uploadProfilePic(){
        imageUri=Uri.parse("android.resource://$packageName/${R.drawable.img_user}")
        storageRefrence=FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageRefrence.putFile(imageUri).addOnSuccessListener{
            Toast.makeText(this@ProfileActivity, "프로필이 정상적으로 업데이트 되었습니다.", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener{
            Toast.makeText(this@ProfileActivity, "프로필 업로드 실패", Toast.LENGTH_SHORT).show()

        }
    }




}