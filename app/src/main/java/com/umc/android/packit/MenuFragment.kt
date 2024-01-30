package com.umc.android.packit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        binding = FragmentMenuBinding.inflate(inflater,container,false)

        inputDummyMenu()

        val mainmenuRVAdapter = MainMenuRVAdapter(getMainMenus())
        mainmenuRVAdapter.setMyItemClickListener(this)
        binding.menuMainRv.adapter = mainmenuRVAdapter

        val sidemenuRVAdapter = SideMenuRVAdapter(getSideMenus())
        sidemenuRVAdapter.setMyItemClickListener(this)
        binding.menuSideRv.adapter = sidemenuRVAdapter


//        // Main Menu 데이터 설정
//        val mainMenuList = listOf("아이템 1", "아이템 2", "아이템 3") // TODO: 실제 데이터로 대체
//        val mainMenuAdapter = MainMenuAdapter(mainMenuList)
//        binding.menuMainRecyclerView.adapter = mainMenuAdapter
//
//        // Side Menu 데이터 설정
//        val sideMenuList = listOf("사이드 아이템 1", "사이드 아이템 2", "사이드 아이템 3") // TODO: 실제 데이터로 대체
//        val sideMenuAdapter = SideMenuAdapter(sideMenuList)
//        binding.menuSideRecyclerView.adapter = sideMenuAdapter
//
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

        return binding.root
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
        val storeId:Int = 1
        menuDatas.apply{
            add(Menu(1, storeId, "메뉴 1", "권장용량/크기",22000,1, R.drawable.store_img_1))
            add(Menu(2, storeId,"메뉴 2", "권장용량/크기", 11000,1,R.drawable.store_img_2))
            add(Menu(3, storeId,"메뉴 3", "권장용량/크기", 13000,1, R.drawable.store_img_3))
            add(Menu(4, storeId,"메뉴 4", "권장용량/크기", 6000,2, R.drawable.store_img_1))
            add(Menu(5, storeId, "메뉴 5", "권장용량/크기", 4000,2,R.drawable.store_img_2))
            add(Menu(6, storeId, "메뉴 6", "권장용량/크기", 3000,2, R.drawable.store_img_3))
        }
    }

    override fun onItemClick(menu: Menu) {
        TODO("Not yet implemented")
    }

}