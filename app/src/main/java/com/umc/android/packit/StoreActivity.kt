package com.umc.android.packit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.android.packit.databinding.ActivityStoreBinding

class StoreActivity : AppCompatActivity() {

    lateinit var binding: ActivityStoreBinding
    private var isStarSelected:Boolean = false
    var storeId:Int = 0

    private val information = arrayListOf("메뉴", "가게 정보", "평점")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initStore()

        val storeAdapter = StoreVPAdapter(this)
        binding.storeContentVp.adapter = storeAdapter
        TabLayoutMediator(binding.storeContentTb, binding.storeContentVp) { tab, position ->
            tab.text = information[position]
        }.attach()

        // ViewPager2의 페이지 변경을 감지하는 콜백 추가
        binding.storeContentVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
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
            isStarSelected = !isStarSelected

            binding.storeStarIv.setImageResource(
                if (isStarSelected) R.drawable.btn_star_select else R.drawable.btn_star_no_select)
        }

        binding.menuCartBtn.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
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


    private fun initStore() {
        val intent = intent
        if (intent != null && storeId == 0) { // storeId가 0일 때만 초기화
            intent.getIntExtra("storeImg", -1).let { storeImg ->
                if (storeImg != -1) {
                    binding.storeBackgroundView.setImageResource(storeImg)
                }
            }
            isStarSelected = intent.getBooleanExtra("star", false)
            binding.storeStarIv.setImageResource(if (isStarSelected) R.drawable.btn_star_select else R.drawable.btn_star_no_select)
            storeId = intent.getIntExtra("storeId", 0)

            Log.d("StoreActivity", "New Menu's store_id: $storeId")

        }
    }

}