package com.umc.android.packit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.android.packit.databinding.ItemCartMenuBinding



class CartRVAdapter(private val menuList: ArrayList<Menu>):RecyclerView.Adapter<CartRVAdapter.ViewHolder>() {

    // 클릭 이벤트 등록
    interface ItemClick {
        fun onRemoveMenu(position : Int) // 닫기 버튼 누르면 메뉴 삭제
        fun onAddMenu(position : Int) //  플러스 버튼 누르면 수량 추가
        fun onSubMenu(position : Int) // 마이너스 버튼 누르면 수량 빼기
    }

    // 아이템 클릭 리스너
    private lateinit var itemClickLinstener : ItemClick

    // 외부 클래스(CartFragment)와 이어짐
    fun onItemClickListener(itemClick : ItemClick) {
        itemClickLinstener = itemClick
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

        // 닫기 버튼 누르면 아이템 삭제
        holder.binding.itemCartClosedBtn.setOnClickListener {
            itemClickLinstener.onRemoveMenu(position)
        }

        // 플러스 버튼 누르면 수량 추가
        holder.binding.itemCartPlusBtn.setOnClickListener {
            itemClickLinstener.onAddMenu(position)
        }

        // 마이너스 버튼 누르면 수량 빼기
        holder.binding.itemCartMinusBtn.setOnClickListener {
            itemClickLinstener.onSubMenu(position)
        }
    }

    // 메뉴 삭제 함수
    fun removeMenu(position:Int) {
        menuList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount) // 아이템 위치 변경을 알림
    }

    // 메뉴 수 추가 함수
    fun addMenu(position: Int) {
        // position이 menuList의 유효한 인덱스 범위라면, 수량 증가
        if (position in 0 until menuList.size) {
            menuList[position].count++
            notifyItemChanged(position)
        }
    }

    // 메뉴 수 빼기 함수
    fun subMenu(position: Int) {
        // 수량이 1 이하로 감소하는 것을 방지
        if (position in 0 until menuList.size && menuList[position].count > 1) {
            menuList[position].count--
            notifyItemChanged(position)
        }
    }

    inner class ViewHolder(val binding: ItemCartMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: Menu){
            binding.itemCartMenuImageIv.setImageResource(menu.menuImg!!)
            binding.itemCartMenuNameTv.text = menu.menu_name
            binding.itemCartMenuPriceTv.text = String.format("%,d 원", menu.price)
            binding.itemCartCountBtn.text = menu.count.toString()
        }
    }
}