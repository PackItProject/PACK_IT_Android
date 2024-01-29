package com.umc.android.packit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.umc.android.packit.databinding.ActivityMainBinding
import com.umc.android.packit.databinding.FragmentStoreListBinding

class MainActivity : AppCompatActivity(){
    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()


    }

    private fun initBottomNavigation(){



        binding.bnvMap.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                // x 버튼 추가해서 없애야만 할거같음...
                R.id.menu_navigation -> {

                    val intent = Intent(this, MapActivity::class.java)
                    startActivity(intent)
                }

                R.id.menu_star -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FavoriteFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

//                R.id.menu_my_packet -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_frm, MypackitFragment())
//                        .commitAllowingStateLoss()
//                    return@setOnItemSelectedListener true
//                }
                R.id.menu_order_list -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, OrderHistoryFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
//                R.id.menu_my_info -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_frm, MyInfoActivity())
//                        .commitAllowingStateLoss()
//                    return@setOnItemSelectedListener true
//                }
            }
            false
        }
    }


}