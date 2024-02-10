package com.umc.android.packit

import android.os.Bundle
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 주문 완료 다이얼로그 표시
        // Check if the intent has a flag to show OrderCompletedFragment
        if (intent.getBooleanExtra("showOrderCompletedFragment", false)) {
            val orderCompletedFragment = OrderCompletedFragment()
            orderCompletedFragment.show(supportFragmentManager, "OrderCompletedFragment")
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
                R.id.mapFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MapFragment())
                        .commit()
                }

                R.id.favoriteFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, FavoriteFragment())
                        .commit()
                }

                R.id.myPackitFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MypackitFragment())
                        .commit()
                }


                R.id.orderHistoryFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, OrderHistoryFragment())
                        .commit()
                }

                R.id.myInfoFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MyInfoFragment())
                        .commit()
                }
            }
            true
        }

    }

    fun goBack() {
        onBackPressed()
    }

    private fun showOrderCompletedFragment() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val orderCompletedFragment = OrderCompletedFragment()
        transaction.replace(R.id.order_completed_layout, orderCompletedFragment)
        transaction.commit()
    }
}