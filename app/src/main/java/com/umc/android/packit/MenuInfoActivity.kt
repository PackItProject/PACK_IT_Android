package com.umc.android.packit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.umc.android.packit.databinding.ActivityMenuInfoBinding
import com.umc.android.packit.databinding.ActivityStoreBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val menu = intent.getSerializableExtra("MenuData") as? Menu
        menu?.let {
            val apiService = ApiClient.retrofitInterface

            apiService.getMenuInfo(it.store_id,it.id).enqueue(object : Callback<List<Menu>> {
                override fun onResponse(call: Call<List<Menu>>, response: Response<List<Menu>>) {
                    if (response.isSuccessful) {
                        val menus = response.body()?.get(0)
                        // Populate UI with storeInfo
                        if (menus != null) {
                            initMenuInfo(menus)
                        }
                        // Populate other views similarly
                    } else {
                        // Handle error
                    }
                }

                override fun onFailure(call: Call<List<Menu>>, t: Throwable) {
                    // Handle failure
                }
            })
        }

        // 뒤로가기 버튼
        binding.menuInfoBackBtnIv.setOnClickListener {
            finish()
        }

        // 장바구니 담기 버튼
        binding.menuInfoAddCartBtn.setOnClickListener {
            val apiService = ApiClient.retrofitInterface
            //val userId = 1 // 사용자 ID를 여기에 설정하세요

            menu?.let { newMenu ->
                val cartItem = CartItem(1, 1, 1, 1)
                apiService.addMenuToCart(cartItem).enqueue(object : Callback<BookmarkResponse> {
                    override fun onResponse(call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                        if (response.isSuccessful) {
                            // 성공적으로 추가되었을 때의 처리
                            val responseBody = response.body()
                            Toast.makeText(this@MenuInfoActivity, responseBody?.message, Toast.LENGTH_SHORT).show()
                        } else {
                            // API 호출은 성공했지만, 서버에서 오류 응답이 왔을 때의 처리
                            Toast.makeText(this@MenuInfoActivity, "장바구니 추가 실패", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                        // API 호출 자체가 실패했을 때의 처리
                        Toast.makeText(this@MenuInfoActivity, "API 호출 실패", Toast.LENGTH_SHORT).show()
                    }
                })


                val sharedPreferences = getSharedPreferences("Cart", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                val savedMenuListJson = sharedPreferences.getString("menuList", null)
                val savedMenuList = if (!savedMenuListJson.isNullOrEmpty()) {
                    val listType = object : TypeToken<ArrayList<Menu>>() {}.type
                    Gson().fromJson<ArrayList<Menu>>(savedMenuListJson, listType)
                } else {
                    ArrayList()
                }

                val newMenuStoreId = newMenu.store_id
                Log.d("MenuInfoActivity", "New Menu's store_id: $newMenuStoreId")

                if (savedMenuList.any { it.store_id == newMenuStoreId }) {
                    // 기존 Cart에서 같은 store_id의 메뉴를 찾아 삭제
                    val updatedMenuList = savedMenuList.filter { it.store_id == newMenuStoreId }.toMutableList()

                    // 중복된 메뉴가 있는지 확인하고, 있으면 수량 증가
                    val existingMenu = savedMenuList.find { it.id == menu.id }
                    if (existingMenu != null) {
                        //Toast.makeText(this, "이미 장바구니에 있는 메뉴입니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        // 장바구니에 없는 메뉴일 경우 새로 추가
                        updatedMenuList.add(newMenu)
                    }

                    // 리스트를 JSON 형태로 변환하여 저장
                    editor.putString("menuList", Gson().toJson(updatedMenuList))
                    editor.apply()

                    //Toast.makeText(this, "메뉴가 장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    // 기존 Cart의 모든 메뉴 삭제 후 새로운 메뉴 추가
                    val updatedMenuList = ArrayList<Menu>()
                    updatedMenuList.add(newMenu)

                    // 리스트를 JSON 형태로 변환하여 저장
                    editor.putString("menuList", Gson().toJson(updatedMenuList))
                    editor.apply()

                    //Toast.makeText(this, "장바구니에 새로운 메뉴가 추가되었습니다.", Toast.LENGTH_SHORT).show()
                }
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
        menu.image?.let {
            Glide.with(this)
                .load(it)
                .into(binding.menuInfoImageIv)
        }
        binding.menuInfoNameTv.text = menu.menu_name
        binding.menuInfoDescriptionTv.text = menu.about_menu
        binding.menuInfoPrice02Tv.text = "${menu.price}원"
        binding.menuInfoSize02Tv.text = menu.containter
        if (menu.insulation == 0){
            binding.menuInfoRequired01Tv.visibility = View.GONE
        }
        if (menu.liquid_seal == 0){
            binding.menuInfoRequired02Tv.visibility = View.GONE
        }
    }
}
