package com.umc.android.packit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.umc.android.packit.databinding.ActivityMainBinding
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.room.Index

private const val TAG_MAP = "map_fragment"
private const val TAG_FAVORITE = "favorite_fragment"


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    // TODO: 로그인 구현 후 삭제
    // 유저 ID
    //private val userID : Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO
        //orderActivity->MainActivity->orderCompletedFragment
        val receivedValue1 = intent.getStringExtra("orderTime")
        val receivedValue2 = intent.getStringExtra("orderStoreName")
        val bundle = Bundle()
        bundle.putString("orderTime", receivedValue1)
        bundle.putString("orderStoreName", receivedValue2)

        if (receivedValue2 != null) {
            Log.d("ykMainActivity OrderstoreName", receivedValue2) //잘나옴
        }

        // 주문 완료 다이얼로그 표시
        // Check if the intent has a flag to show OrderCompletedFragment
        if (intent.getBooleanExtra("showOrderCompletedFragment", false)) {
            val orderCompletedFragment = receivedValue1?.let{
                OrderCompletedFragment.newInstance(it)
            }

            if (orderCompletedFragment != null) {
                orderCompletedFragment.show(supportFragmentManager, "OrderCompletedFragment")
            }


        }

        // 바텀 네비게이션
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fl_container) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomnavi.setupWithNavController(navController)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MapFragment())
                .commit()
        }

        binding.bottomnavi.setOnItemSelectedListener { item ->
            when (item.itemId) {
                // userId 내용담고 화면 전환
                R.id.mapFragment -> {
                    createFragmentWithBundle(MapFragment())
                }

                R.id.favoriteFragment -> {
                    createFragmentWithBundle(FavoriteFragment())
                }

                R.id.myPackitFragment -> {
                    createFragmentWithBundle(MypackitFragment())
                }


                R.id.orderHistoryFragment -> {
                    createFragmentWithBundle(OrderHistoryFragment())
                }

                R.id.myInfoFragment -> {
                    createFragmentWithBundle(MyInfoFragment())
                }
            }
            true
        }

    }

    // 프래그먼트 이동 함수
    fun createFragmentWithBundle(fragment: Fragment) {
        // 화면 전환
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}