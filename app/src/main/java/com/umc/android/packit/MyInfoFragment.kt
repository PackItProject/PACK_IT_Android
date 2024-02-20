package com.umc.android.packit

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Outline
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kakao.sdk.user.UserApiClient
import com.umc.android.packit.databinding.FragmentMyInfoBinding

class MyInfoFragment : Fragment() {

    private lateinit var binding: FragmentMyInfoBinding
    private lateinit var storageReference: StorageReference
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyInfoBinding.inflate(inflater, container, false) // 바인딩 생성
        receiveHomeData() //프로필 사진 띄우기

        //프로필사진 모서리 동그랗게
        binding.profileImgIv.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                val cornerRadius = resources.getDimensionPixelSize(R.dimen.corner_radius)
                outline.setRoundRect(0, 0, view.width, view.height, cornerRadius.toFloat())
            }
        }
        binding.profileImgIv.clipToOutline = true

        // SharedPreferences에서 닉네임을 불러와서 name_tv에 표시
        val sharedPreferences = requireActivity().getSharedPreferences("sp1", Context.MODE_PRIVATE)
        val nickname = sharedPreferences.getString("name", "데이터 없음")
        binding.profileNameTv.text = nickname



        //비트맵으로 한번해봐야겠다..
        //TODO: 프로필액티비티에서 uri받아와서 프로필 사진 띄우기 왜안되지?
    /*    val receivedUriString = arguments?.getString("URIKEY")
        Log.d("MyInfoFragment", "Received URI: $receivedUriString")

        if ( receivedUriString != null) {
            Glide.with(this)
                .load(Uri.parse( receivedUriString))
                .placeholder(R.drawable.img_no_img)
                .error(R.drawable.img_no_img)
                .into(binding.profileImgIv)
        } else {
            Log.e("MyInfoFragment", "Received URI is null.")
        }*/

     /*   val receivedUriString = arguments?.getString("profile_img_url")
        Log.d("MyInfoFragment", "Received URI: $receivedUriString")
        val bundle = arguments
        Log.d("MyInfoFragment", "Bundle contents: $bundle")
        // Bundle contents: null
        //TODO: 로그 찍어보니까 Received URI: null이래..

        if (receivedUriString != null) {
            Log.d("MyInfoFragment", "Setting image URI")

            Glide.with(this)
                .load(Uri.parse(receivedUriString))
                .into(profileImgIv)
        }else {
            Log.e("MyInfoFragment", "Received URI is null.")
        }
*/
    /*    // 프로필 사진 URI를 관찰하여 변경이 있을 때 이미지뷰에 반영
        profileViewModel.profileImageUri.observe(viewLifecycleOwner, Observer { uri ->
            // 링크가 없는 경우 기본 이미지를 보여주기 위함
            if(uri.isNullOrEmpty()) {
                profileImgIv.setImageResource(R.drawable.img_user)
            }
            else{  //링크가 있는 경우 링크에서 프로필 이미지를 가져와서 보여준다.
                binding.profileImgIv.setImageURI(uri)
                Glide.with(this)
                    .load(uri)
                    .into(profileImgIv)
            }
        })*/

       /* //카카오 api에서 받아온대로 회원정보 띄우기
        UserApiClient.instance.me { user, error ->
            binding.profileNameTv.text = "${user?.kakaoAccount?.profile?.nickname}"
            binding.profileEmailTv.text = "${user?.kakaoAccount?.profile?.email}"
        }*/

        binding.favoriteBtn.setOnClickListener {
            // 즐겨찾기 버튼을 클릭했을 때 FavoriteFragment로 이동하는 코드
            navigateToFavoriteFragment()


        }
    /*    binding.couponBtn.setOnClickListener {
            navigateToCouponFragment()
        }*/

         binding.myRateBtn.setOnClickListener {
             navigateToRateFragment()
         }

        binding.noticeBtn.setOnClickListener {
            navigateToNoticeFragment()
        }

        binding.servicePolicyBtn.setOnClickListener {
            navigateToServicePolicyFragment()
        }

        binding.removeAccountBtn.setOnClickListener{
            val dialogView = LayoutInflater.from(requireActivity()).inflate(R.layout.remove_account_dialog, null)
            val builder = AlertDialog.Builder(requireActivity())
                .setView(dialogView)
                .setTitle("회원 탈퇴")
            val alertDialog = builder.show()
            val okBtn = alertDialog.findViewById<Button>(R.id.ok_btn)
            okBtn.setOnClickListener {


                UserApiClient.instance.unlink { error ->
                    if (error != null) {
                        Log.e(TAG, "연결 끊기 실패", error)
                    }
                    else {
                        Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                        Toast.makeText(
                            requireActivity(), "정상적으로 회원 탈퇴되었습니다. 로그인 화면으로 이동합니다.", Toast.LENGTH_SHORT
                        ).show()

//                        navigateToMapFragment() //메인화면으로 이동
                        alertDialog.dismiss() //다이얼로그는 닫기

                        val intent = Intent(requireActivity(), LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        requireActivity().finish()

                    }
                }






                //TODO: 카카오 api 연결 끊기 = 회원탈퇴
                //TODO: 로그인 구현 전이므로 아래 코드는 주석처리하였음
                //참고:https://developers.kakao.com/docs/latest/ko/kakaologin/android
                //https://kdjun97.github.io/kotlin/kotlin-kakao-login/
             /*   UserApiClient.instance.unlink { error ->
                    if (error != null) {
                        Log.e(TAG, "연결 끊기 실패", error)
                    }
                    else {
                        Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                    }
                }*/

            }
            val cancelBtn = alertDialog.findViewById<Button>(R.id.cancel_btn)
            //취소 버튼 클릭시 아무것도 하지 않고 다이얼로그 닫기
            cancelBtn.setOnClickListener {
                alertDialog.dismiss()

            }

        }

        return binding.root

    }
 /*   override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        selectedImageUri?.let { outState.putParcelable(KEY_IMAGE_URI, it) }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            selectedImageUri = it.getParcelable(KEY_IMAGE_URI)
            selectedImageUri?.let { uri ->
                binding.profileImgIv.setImageURI(uri)
            }
        }
    }*/
    //FavoriteFragment()로 이동
    private fun navigateToFavoriteFragment() {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val favoriteFragment =FavoriteFragment.newInstance()
        fragmentTransaction.replace(R.id.cl_my_info, favoriteFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


    private fun navigateToRateFragment() {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val rateFragment = RateFragment.newInstance()
        fragmentTransaction.replace(R.id.cl_my_info, rateFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun navigateToNoticeFragment() {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val noticeFragment = NoticeFragment.newInstance()
        fragmentTransaction.replace(R.id.cl_my_info, noticeFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun navigateToServicePolicyFragment() {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val servicePolicyFragment =ServicePolicyFragment.newInstance()
        fragmentTransaction.replace(R.id.cl_my_info, servicePolicyFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun navigateToMapFragment() {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val mapFragment =MapFragment.newInstance()
        fragmentTransaction.replace(R.id.cl_my_info, mapFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun receiveHomeData() {

        val byteArray = arguments?.getByteArray("img")

        if (byteArray != null) {
            // ByteArray를 Bitmap으로 변환
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

            Log.d("MyInfoFragment", "Received ByteArray size: ${byteArray.size}")
            Log.d("MyInfoFragment", "Decoded Bitmap: $bitmap")

            // 앨범 커버 이미지에 Bitmap 설정
            binding.profileImgIv.setImageBitmap(bitmap)
        } else {
            Log.e("MyInfoFragment", "Received ByteArray is null.")
            //TODO:Received ByteArray is null.
        }

        //닉네임 설정
        binding.profileNameTv.text = arguments?.getString("nickname")?:"강희정"

    }




}


