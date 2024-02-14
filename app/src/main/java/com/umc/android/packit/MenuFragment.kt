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

class MenuFragment : Fragment(), MainMenuRVAdapter.MyItemClickListener, SideMenuRVAdapter.MyItemClickListener {

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

        inputDummyMenu(storeId)

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

    private fun inputDummyMenu(storeId: Int) {
//        var storeId = arguments?.getInt("storeId", -1)!!
        //var storeId = 1
        menuDatas.apply {
            add(Menu(1, storeId, "메뉴 1" ,"고기 토마토 소스와 함께 얇은 면의 스파게티를 곁들인 파스타.\n마늘, 양파, 토마토, 베이컨.","270 * 130 mm", 22000,0,0, "더운 날씨에 상할 수 있으므로 바로 드시는 것을\n" +
                    "권장드립니다.",1, 1,R.drawable.store_img_1))
            add(Menu(2, storeId, "메뉴 2", "풍부한 크림과 파마산 치즈의 풍미가 일품.\n양파, 치즈.","250 * 130 mm", 11000,0,0, "알레르기 관련 요청사항은 주문 시에 명시해주세요." +
                    "주문 시에 명시해주세요.",1, 1, R.drawable.store_img_2))
            add(Menu(3, storeId, "메뉴 3", "스파게티에 계란, 파마산 치즈, 페이콘, 후추 등을 사용하여 만든 고소하고 크리미한 파스타.\n계란,치즈,페이콘,후추.","270 * 150 mm", 13000, 0,0, "주문 시 주문 항목, 수량, 옵션 등을 정확하게 기재해주세요.\n" +
                    "",1,1, R.drawable.store_img_3))
            add(Menu(4, storeId, "메뉴 4", "올리브 오일, 마늘, 후추로 만든 클래식 알리오 올리오 파스타.\n올리브 오일, 마늘, 후추.","권장용량/크기", 6000, 0,0, "음식 수령 시간을 정확하게 확인하고 주문을 진행해주세요.\n" +
                    "",1,2, R.drawable.store_img_1))
            add(Menu(5, storeId, "메뉴 5", "라자냐 시트, 고기 소스, 토마토 소스, 치즈로 만든 라자냐.\n고기, 토마토.","권장용량/크기", 4000, 0,0, "공동 현관 비밀번호가 있을 경우 주문 메시지란에 적어주세요.\n" +
                    "",1,2, R.drawable.store_img_2))
            add(Menu(6, storeId, "메뉴 6", "올리브, 토마토, 마늘로 누구나 좋아하는 맛.\n올리브, 토마토, 마늘.","권장용량/크기", 3000, 0,0, "주문 전 정확한 건물명, 동, 호 등 상세 주소를 확인해주세요.\n" +
                    "",1,2, R.drawable.store_img_3))
        }
    }

    override fun onItemClick(menu: Menu) {
        val intent = Intent(requireContext(), MenuInfoActivity::class.java)
        intent.putExtra("MenuData", menu)
        startActivity(intent)
    }
}