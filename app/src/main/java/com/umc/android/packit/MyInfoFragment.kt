package com.umc.android.packit

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Outline
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.umc.android.packit.databinding.FragmentMyInfoBinding


//갤러리에서 불러와서 프로필 사진 편집할 수 있게 해봄- DB가 없어서 바텀 이동시 사진이 사라짐


class MyInfoFragment : Fragment() {

    var mainActivity: MainActivity? = null
    private lateinit var binding: FragmentMyInfoBinding
    private var selectedImageUri: Uri? = null
    private val profileViewModel: ProfileViewModel by activityViewModels()

    companion object {
        const val TAG: String = "로그"
        private const val KEY_IMAGE_URI = "key_image_uri"
        fun newInstance(): MyInfoFragment {
            return MyInfoFragment()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyInfoBinding.inflate(inflater, container, false) // 바인딩 생성
        val profileImgIv= binding.profileImgIv


        //프로필사진 모서리 동그랗게
        profileImgIv.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                val cornerRadius = resources.getDimensionPixelSize(R.dimen.corner_radius)
                outline.setRoundRect(0, 0, view.width, view.height, cornerRadius.toFloat())
            }
        }
        profileImgIv.clipToOutline = true

        initImageViewProfile()

        //TODO: 프로필액티비티에서 uri받아와서 프로필 사진 띄우기 왜안되지?
        val receivedUriString = arguments?.getString("URIKEY")
        Log.d("MyInfoFragment", "Received URI: $receivedUriString")

        if ( receivedUriString != null) {
            Glide.with(this)
                .load(Uri.parse( receivedUriString))
                .placeholder(R.drawable.img_no_img)
                .error(R.drawable.img_no_img)
                .into(profileImgIv)
        } else {
            Log.e("MyInfoFragment", "Received URI is null.")
        }

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
                Toast.makeText(
                    requireActivity(), "정상적으로 회원 탈퇴되었습니다. 메인 화면으로 이동합니다.", Toast.LENGTH_SHORT
                ).show()
                navigateToMapFragment() //메인화면으로 이동
                alertDialog.dismiss() //다이얼로그는 닫기

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
    override fun onSaveInstanceState(outState: Bundle) {
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
    }
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

    override fun onAttach(context: Context) { // Ctrl + O를 통해 입력
        super.onAttach(context)
        if(context is MainActivity) mainActivity = context // 메인 액티비티인 것을 확인하고 캐스팅해서 프로퍼티에 저장
    }

    private fun initImageViewProfile() {
       /* val profileImgIv: ImageView = binding.profileImgIv
*/
        binding.profileImgIv.setOnClickListener {
            when {
                // 갤러리 접근 권한이 있는 경우
                ContextCompat.checkSelfPermission(
                    requireActivity(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                -> {
                    navigateGallery()
                }

                // 갤러리 접근 권한이 없는 경우
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                -> {
                    showPermissionContextPopup()
                }

                // 권한 요청 하기(requestPermissions) -> 갤러리 접근(onRequestPermissionResult)
                else -> requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1000
                )
            }

        }
    }

    // 권한 요청 승인 이후 실행되는 함수
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    navigateGallery()
                else
                    Toast.makeText(requireActivity(), "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                //
            }
        }
    }

    private fun navigateGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        // 가져올 컨텐츠들 중에서 Image 만을 가져온다.
        intent.type = "image/*"
        // 갤러리에서 이미지를 선택한 후, 프로필 이미지뷰를 수정하기 위해 갤러리에서 수행한 값을 받아오는 startActivityForeResult를 사용한다.
        startActivityForResult(intent, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        // 예외처리
        if (resultCode != Activity.RESULT_OK)
            return

        when (requestCode) {
            // 2000: 이미지 컨텐츠를 가져오는 액티비티를 수행한 후 실행되는 Activity 일 때만 수행하기 위해서
            2000 -> {
                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null) {
                    binding.profileImgIv.setImageURI(selectedImageUri)
                    this.selectedImageUri = selectedImageUri //프로필 사진 저장하여 바텀네비게이션 이동하여도 유지하도록
                } else {
                    Toast.makeText(requireActivity(), "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(requireActivity(), "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(requireActivity())
            .setTitle("권한이 필요합니다.")
            .setMessage("프로필 이미지를 바꾸기 위해서는 갤러리 접근 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }
}


