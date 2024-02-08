package com.umc.android.packit
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.android.packit.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment(), FavoriteRVAdapter.MyItemClickListener {

    lateinit var binding: FragmentFavoriteBinding
    private var storeDatas = ArrayList<Store>()
    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }
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

        updateFavoriteCount()

        return binding.root
    }

    private fun getFavoriteStores(): List<Store> {
        // storeDatas 중 star 값이 true인 항목만 필터링하여 반환
        return storeDatas.filter { it.star == true }
    }

    private fun updateFavoriteCount() {
        val favoriteCount = (binding.favoriteMainRecyclerView.adapter as? FavoriteRVAdapter)?.getFavoriteCount() ?: 0
        binding.favoriteSumTv.text = getString(R.string.favorite_sum, favoriteCount)
    }

    override fun onItemClick(store: Store) {
        // 아이템 클릭 시 StoreActivity를 시작
        val intent = Intent(requireContext(), StoreActivity::class.java)
        intent.putExtra("storeImg", store.storeImg ?: -1) // storeImg가 null이 아니면 해당 값, null이면 -1을 전달

        startActivity(intent)
    }

    override fun onStarClick(store: Store) {
        store.star = !store.star!! // Toggle the value
        updateStoreStarImage(store)

        val favoriteRVAdapter = FavoriteRVAdapter(getFavoriteStores())
        favoriteRVAdapter.setMyItemClickListener(this)
        binding.favoriteMainRecyclerView.adapter = favoriteRVAdapter

        updateFavoriteCount()
    }


    private fun updateStoreStarImage(store: Store) {
        val adapter = binding.favoriteMainRecyclerView.adapter as? FavoriteRVAdapter
        adapter?.updateStoreStarImage(store)
    }

}