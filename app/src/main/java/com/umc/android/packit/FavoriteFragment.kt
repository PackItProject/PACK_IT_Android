package com.umc.android.packit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.android.packit.databinding.FragmentFavoriteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteFragment : Fragment(), FavoriteRVAdapter.MyItemClickListener {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteRVAdapter: FavoriteRVAdapter
    private val storeDatas = ArrayList<Store>()

    // TODO: 로그인 구현 후 삭제
    // mainAtctivity에서 유저 ID 가져오기
    //private val userID = arguments?.getInt("userId", 1)

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
        // 현재 유저 아이디 체크
        //Toast.makeText(requireContext(), "현재 유저 아이디: ${userID}", Toast.LENGTH_SHORT).show()

        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        favoriteRVAdapter = FavoriteRVAdapter(storeDatas)
        favoriteRVAdapter.setMyItemClickListener(this)
        binding.favoriteMainRecyclerView.apply {
            adapter = favoriteRVAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        loadData()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val apiService = ApiClient.retrofitInterface

        val userId = 1 // 사용자 ID를 여기에 설정하세요

        apiService.getBookmarkedStores(userId).enqueue(object : Callback<List<Store>> {
            override fun onResponse(call: Call<List<Store>>, response: Response<List<Store>>) {
                if (response.isSuccessful) {
                    val stores = response.body()
                    stores?.let {
                        storeDatas.apply {
                            clear()
                            addAll(it)
                        }
                        favoriteRVAdapter.notifyDataSetChanged()
                        updateFavoriteCount()
                    }
                } else {
                    Toast.makeText(requireContext(), "북마크 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Store>>, t: Throwable) {
                Toast.makeText(requireContext(), "실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateFavoriteCount() {
        val favoriteCount = storeDatas.size
        binding.favoriteSumTv.text = getString(R.string.favorite_sum, favoriteCount)
    }

    override fun onItemClick(store: Store) {
        val intent = Intent(requireContext(), StoreActivity::class.java)

        // userId 전달
        //intent.putExtra("userId", userID)

        intent.putExtra("storeId", store.store_id ?: 0)
        intent.putExtra("storeImg", store.image ?: "")
        intent.putExtra("storeName", store.store_name ?: "")
        startActivity(intent)
    }

    override fun onStarClick(store: Store) {
        if (store.is_bookmarked == 1) store.is_bookmarked = 0 else store.is_bookmarked = 1

        val apiService = ApiClient.retrofitInterface
        val userId = 1 // 사용자 ID를 여기에 설정하세요

        apiService.changeBookmarkStatus(store.store_id, userId).enqueue(object : Callback<BookmarkResponse> {
            override fun onResponse(call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                if (response.isSuccessful) {
                    updateStoreStarImage(store)
                    updateFavoriteCount()
                } else {
                    Toast.makeText(requireContext(), "북마크 상태 변경 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateStoreStarImage(store: Store) {
        favoriteRVAdapter.updateStoreStarImage(store)
    }
}