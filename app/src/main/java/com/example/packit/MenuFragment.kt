package com.example.packit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.packit.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater,container,false)

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
}