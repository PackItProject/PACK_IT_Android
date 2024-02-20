package com.umc.android.packit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.android.packit.databinding.ActivityStoreBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreActivity : AppCompatActivity() {

    lateinit var binding: ActivityStoreBinding
    private var isStarSelected:Int = 0
    var storeId : Int = 0
    var userId : Int = 1

    private val information = arrayListOf("메뉴", "가게 정보", "평점")

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 현재 유저 아이디 체크
        //Toast.makeText(this, "현재 유저 아이디: ${userID}", Toast.LENGTH_SHORT).show()

//        val sharedPreferencesManager = SharedPreferencesManager(this@StoreActivity)
//        userId = sharedPreferencesManager.getUserId() // 사용자 ID를 여기에 설정하세요

        // 가게 정보 초기화
        initStore()

        val storeAdapter = StoreVPAdapter(this)
        binding.storeContentVp.adapter = storeAdapter
        TabLayoutMediator(binding.storeContentTb, binding.storeContentVp) { tab, position ->
            tab.text = information[position]
        }.attach()

        // ViewPager2의 페이지 변경을 감지하는 콜백 추가
        binding.storeContentVp.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                // 페이지가 선택될 때마다 카트 버튼 표시 여부 업데이트
                showHideCartButton(information[position] == "메뉴")
            }
        })

        binding.storeBackIv.setOnClickListener {
            finish()
        }

        binding.storeStarIv.setOnClickListener {
            if (isStarSelected == 1) isStarSelected = 0 else isStarSelected = 1

            val apiService = ApiClient.retrofitInterface

            apiService.changeBookmarkStatus(storeId, userId).enqueue(object :
                Callback<BookmarkResponse> {
                override fun onResponse(call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                    if (response.isSuccessful) {
                        // 북마크 상태 변경 성공
                        binding.storeStarIv.setImageResource(
                            if (isStarSelected == 1) R.drawable.btn_star_select else R.drawable.btn_star_no_select)

                    } else {
                        // 북마크 상태 변경 실패
                    }
                }

                override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                    // 네트워크 오류 등으로 인한 실패
                }
            })

            binding.storeStarIv.setImageResource(
                if (isStarSelected == 1) R.drawable.btn_star_select else R.drawable.btn_star_no_select
            )
        }

        binding.menuCartBtn.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("storeName", binding.storeImageTv.text.toString())
            intent.putExtra("storeId", storeId)
            startActivity(intent)
        }
    }

    private fun showHideCartButton(show: Boolean) {
        if (show) {
            binding.menuCartBackground.visibility = View.VISIBLE
        } else {
            binding.menuCartBackground.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()

        // 현재 선택된 탭의 인덱스를 가져오기
        val currentTabIndex = binding.storeContentVp.currentItem

        // 현재 탭이 "메뉴" 탭이라면, 카트 버튼을 표시
        if (information[currentTabIndex] == "메뉴") {
            showHideCartButton(true)
        } else {
            // 다른 탭이라면, 카트 버튼을 숨김
            showHideCartButton(false)
        }
    }


    // 가게 정보 초기화
    private fun initStore() {
        val intent = intent

        // 가게 이름 설정
        binding.storeImageTv.text = intent.getStringExtra("storeName")

        // 가게 이미지 설정
        if (intent != null && storeId == 0) { // storeId가 0일 때만 초기
            intent.getStringExtra("storeImg").let { storeImg ->
                if (storeImg != null) {
                    Glide.with(this)
                        .load(storeImg)
                        .into(binding.storeBackgroundView)
                }
            }
            isStarSelected = intent.getIntExtra("star", 0)
            binding.storeStarIv.setImageResource(if (isStarSelected == 1) R.drawable.btn_star_select else R.drawable.btn_star_no_select)

            // storeId 갱신
            storeId = intent.getIntExtra("storeId", 0)
        }
    }

}

