package com.umc.android.packit
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.android.packit.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment(), FavoriteRVAdapter.MyItemClickListener {

    lateinit var binding: FragmentFavoriteBinding
    private var storeDatas = ArrayList<Store>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)

        storeDatas.apply{
            add(Store(1, "가게 이름", "address 1", "영업 중","평점 4.5",true, R.drawable.store_img_1))
            add(Store(2, "옥루", "address 2", "영업 종료","평점 4.5",false,R.drawable.store_img_2))
            add(Store(3, "선식당", "address 3", "영업 중","평점 4.5", false,R.drawable.store_img_3))
            add(Store(4, "가게 이름", "address 1", "영업 중","평점 4.5", true,R.drawable.store_img_1))
            add(Store(5, "옥루", "address 2", "영업 종료","평점 4.5",false,R.drawable.store_img_2))
            add(Store(6, "선식당", "address 3", "영업 중","평점 4.5",true, R.drawable.store_img_3))

        }

        val favoriteRVAdapter = FavoriteRVAdapter(getFavoriteStores())
        favoriteRVAdapter.setMyItemClickListener(this)
        binding.favoriteMainRecyclerView.adapter = favoriteRVAdapter

        return binding.root
    }

    private fun getFavoriteStores(): List<Store> {
        // storeDatas 중 star 값이 true인 항목만 필터링하여 반환
        return storeDatas.filter { it.star == true }
    }
    override fun onItemClick(store: Store) {

    }

    override fun onStarClick(store: Store) {
        store.star = !store.star!! // Toggle the value
        updateStoreStarImage(store)
    }

    private fun updateStoreStarImage(store: Store) {
        val adapter = binding.favoriteMainRecyclerView.adapter as? FavoriteRVAdapter
        adapter?.updateStoreStarImage(store)
    }

}