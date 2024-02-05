package com.umc.android.packit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.android.packit.databinding.ItemCartMenuBinding


class CartRVAdapter(private val menuList: List<OrderMenu>):RecyclerView.Adapter<CartRVAdapter.ViewHolder>() {

    // 클릭 이벤트 등록
    interface ItemClick {
        fun onDeleteMenu(position : Int) // 닫기 버튼 누르면 메뉴 삭제
        fun onAddMenu() //  플러스 버튼 누르면 수량 추가
        fun onSubMenu() // 마이너스 버튼 누르면 수량 빼기
    }

    // 아이템 클릭 리스너
    private lateinit var itemClickLinstener : ItemClick

    // 외부 클래스(CartFragment)와 이어짐
    fun onItemClickListener() {

    }

    // 개수
    override fun getItemCount(): Int = menuList.size

    // 뷰홀더 생성
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CartRVAdapter.ViewHolder {
        val binding:ItemCartMenuBinding = ItemCartMenuBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    // 뷰홀더 바인딩
    override fun onBindViewHolder(holder: CartRVAdapter.ViewHolder, position: Int) {
        holder.bind(menuList[position])

        //
        holder.binding.itemCartClosedBtn.setOnClickListener {
//            itemClickLinstener.onDeleteMenu()
        }

        //
        holder.binding.itemCartPlusBtn.setOnClickListener {

        }

        //
        holder.binding.itemCartMinusBtn.setOnClickListener {

        }
    }


    inner class ViewHolder(val binding: ItemCartMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: OrderMenu){
            binding.itemCartMenuNameTv.text = menu.menu_name
            binding.itemCartMenuPriceTv.text = menu.price.toString() + " 원"
            binding.itemCartCountBtn.text = menu.count.toString()
        }
    }
}