package com.umc.android.packit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.umc.android.packit.databinding.FragmentMenuBinding

class MenuFragment : Fragment(), MainMenuRVAdapter.MyItemClickListener, SideMenuRVAdapter.MyItemClickListener {

    lateinit var binding: FragmentMenuBinding
    private var menuDatas = ArrayList<Menu>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)

        inputDummyMenu()

        val mainmenuRVAdapter = MainMenuRVAdapter(getMainMenus())
        mainmenuRVAdapter.setMyItemClickListener(this)
        binding.menuMainRv.adapter = mainmenuRVAdapter

        val sidemenuRVAdapter = SideMenuRVAdapter(getSideMenus())
        sidemenuRVAdapter.setMyItemClickListener(this)
        binding.menuSideRv.adapter = sidemenuRVAdapter

        // TODO: 장바구니에 물품이 있을 경우 장바구니 보러가기 버튼 표시
//        // 장바구니 버튼 클릭 이벤트 처리
//        binding.menuCartBtn.setOnClickListener {
//            // TODO: 장바구니 화면으로 이동하면서 필요한 데이터 전달
//            val action = MenuFragmentDirections.actionMenuFragmentToCartFragment(/* pass necessary data here */)
//            findNavController().navigate(action)
//
//            // 예시로 Toast 메시지 출력
//            Toast.makeText(requireContext(), "장바구니를 보여줍니다.", Toast.LENGTH_SHORT).show()
//        }

        binding.menuMainTitleBtn.setOnClickListener {
            it.isSelected = !it.isSelected
            updateButtonUI(it as Button, 1)
        }

        binding.menuSideTitleBtn.setOnClickListener {
            it.isSelected = !it.isSelected
            updateButtonUI(it as Button, 2)
        }

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
        return menuDatas.filter { it.category == 1 }
    }

    private fun getSideMenus(): List<Menu> {
        // menuData 중 category 값이 2인 항목만 필터링하여 반환
        return menuDatas.filter { it.category == 2 }
    }

    private fun inputDummyMenu() {
        // val storeId = arguments?.getInt("storeId", -1)!!
        val storeId: Int = 1
        menuDatas.apply {
            add(Menu(1, storeId, "메뉴 1" ,"abcdefghijklmnopqrstuvwxyz","270 * 130 mm", 22000,0,0, "더운 날씨에 상할 수 있으므로 바로 드시는 것을\n" +
                    "권장드립니다.",1, 1,R.drawable.store_img_1))
            add(Menu(2, storeId, "메뉴 2", "dgq","250 * 130 mm", 11000,0,0, "더운 날씨에 상할 수 있으므로 바로 드시는 것을\n" +
                    "권장드립니다.",1, 1, R.drawable.store_img_2))
            add(Menu(3, storeId, "메뉴 3", "ddkfja","270 * 150 mm", 13000, 0,0, "더운 날씨에 상할 수 있으므로 바로 드시는 것을\n" +
                    "권장드립니다.",1,1, R.drawable.store_img_3))
            add(Menu(4, storeId, "메뉴 4", "dbsddbsb","권장용량/크기", 6000, 0,0, "더운 날씨에 상할 수 있으므로 바로 드시는 것을\n" +
                    "권장드립니다.",1,2, R.drawable.store_img_1))
            add(Menu(5, storeId, "메뉴 5", "dgeg","권장용량/크기", 4000, 0,0, "더운 날씨에 상할 수 있으므로 바로 드시는 것을\n" +
                    "권장드립니다.",1,2, R.drawable.store_img_2))
            add(Menu(6, storeId, "메뉴 6", "dddd","권장용량/크기", 3000, 0,0, "더운 날씨에 상할 수 있으므로 바로 드시는 것을\n" +
                    "권장드립니다.",1,2, R.drawable.store_img_3))
        }
    }

    override fun onItemClick(menu: Menu) {
        val intent = Intent(requireContext(), MenuInfoActivity::class.java)
        intent.putExtra("MenuData", menu)
        startActivity(intent)
    }
}