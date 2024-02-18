package com.umc.android.packit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.umc.android.packit.databinding.FragmentMenuBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuFragment : Fragment(), MainMenuRVAdapter.MyItemClickListener, SideMenuRVAdapter.MyItemClickListener {

    private lateinit var mainmenuRVAdapter : MainMenuRVAdapter
    private lateinit var sidemenuRVAdapter : MainMenuRVAdapter
    lateinit var binding: FragmentMenuBinding
    private var menuDatas = ArrayList<Menu>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val storeId = arguments?.getInt("storeId", -1) ?: -1
        Log.d("MenuFragment", "New Menu's store_id: $storeId")

        binding = FragmentMenuBinding.inflate(inflater, container, false)

        val apiService = ApiClient.retrofitInterface
        apiService.getStoreMenus(storeId).enqueue(object : Callback<List<Menu>> {
            override fun onResponse(call: Call<List<Menu>>, response: Response<List<Menu>>) {
                if (response.isSuccessful) {
                    val menus = response.body()
                    menus?.let {
                        menuDatas.addAll(it)
                        val mainmenuRVAdapter = MainMenuRVAdapter(getMainMenus())
                        val sidemenuRVAdapter = SideMenuRVAdapter(getSideMenus())
                        mainmenuRVAdapter.setMyItemClickListener(this@MenuFragment)
                        sidemenuRVAdapter.setMyItemClickListener(this@MenuFragment)
                        binding.menuMainRv.adapter = mainmenuRVAdapter
                        binding.menuSideRv.adapter = sidemenuRVAdapter
                        if (getSideMenus().isEmpty()){
                            binding.menuSideTv.visibility = View.GONE
                        }
                        if (getMainMenus().isEmpty()){
                            binding.menuMainTv.visibility = View.GONE
                        }
                    }
                } else {
                    // Handle error
                    Log.e("MenuFragment", "Failed to fetch menu data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Menu>>, t: Throwable) {
                // Handle failure
                Log.e("MenuFragment", "Failed to fetch menu data: ${t.message}")
            }
        })


//        binding.menuMainTitleBtn.setOnClickListener {
//            it.isSelected = !it.isSelected
//            updateButtonUI(it as Button, 1)
//        }
//
//        binding.menuSideTitleBtn.setOnClickListener {
//            it.isSelected = !it.isSelected
//            updateButtonUI(it as Button, 2)
//        }

        return binding.root
    }

    private fun updateButtonUI(button: Button, category: Int) {
        var btn: Button = if (category == 1) {
            binding.menuSideTitleBtn
        } else {
            binding.menuMainTitleBtn
        }

        if (button.isSelected) {
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            button.setBackgroundResource(R.drawable.rounded_button_bg_pressed)
            if (btn.isSelected) {
                btn.isSelected = !btn.isSelected
                btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
                btn.setBackgroundResource(R.drawable.rounded_button_bg)
            }
        } else {
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
            button.setBackgroundResource(R.drawable.rounded_button_bg)
        }
    }

    private fun getMainMenus(): List<Menu> {
        // menuData 중 category 값이 1인 항목만 필터링하여 반환
        return menuDatas.filter { it.menu_category == 1 }
    }

    private fun getSideMenus(): List<Menu> {
        // menuData 중 category 값이 2인 항목만 필터링하여 반환
        return menuDatas.filter { it.menu_category == 0 }
    }


    override fun onItemClick(menu: Menu) {
        val intent = Intent(requireContext(), MenuInfoActivity::class.java)
        intent.putExtra("MenuData", menu)
        startActivity(intent)
    }
}