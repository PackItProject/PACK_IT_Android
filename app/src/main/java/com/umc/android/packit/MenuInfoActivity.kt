package com.umc.android.packit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.umc.android.packit.databinding.ActivityMenuInfoBinding
import com.umc.android.packit.databinding.ActivityStoreBinding

class MenuInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val menu = intent.getSerializableExtra("MenuData") as? Menu
        menu?.let {
            initMenuInfo(it)
        }

        binding.menuInfoAddCartBtn.setOnClickListener {
            menu?.let {
                // 메뉴를 SharedPreferences에 추가
                saveMenuToSharedPreferences(it)
            }
            finish()
        }
    }

    private fun saveMenuToSharedPreferences(menu: Menu) {
        val sharedPreferences = getSharedPreferences("Cart", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // 이전에 저장된 메뉴 리스트를 가져옴
        val savedMenuListJson = sharedPreferences.getString("menuList", null)
        val savedMenuList = if (!savedMenuListJson.isNullOrEmpty()) {
            val listType = object : TypeToken<ArrayList<Menu>>() {}.type
            Gson().fromJson<ArrayList<Menu>>(savedMenuListJson, listType)
        } else {
            ArrayList()
        }

        // 중복된 메뉴가 있는지 확인하고, 있으면 수량 증가
        val existingMenu = savedMenuList.find { it.id == menu.id }
        if (existingMenu != null) {
            existingMenu.count++
        } else {
            // 장바구니에 없는 메뉴일 경우 새로 추가
            savedMenuList.add(menu)
        }

        // 리스트를 JSON 형태로 변환하여 저장
        editor.putString("menuList", Gson().toJson(savedMenuList))
        editor.apply()

        Toast.makeText(this, "메뉴가 장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show()
    }

    // 나머지 부분은 그대로 유지

    private fun initMenuInfo(menu: Menu) {
        // 메뉴 정보 초기화
        binding.menuInfoImageIv.setImageResource(menu?.menuImg!!)
        binding.menuInfoNameTv.text = menu.menu_name
        binding.menuInfoDescriptionTv.text = menu.about_menu
        binding.menuInfoPrice02Tv.text = "${menu.price}원"
        binding.menuInfoSize02Tv.text = menu.containers
        binding.menuInfoCautionMessageTv.text = menu.notice
    }
}
