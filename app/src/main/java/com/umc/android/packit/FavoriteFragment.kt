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

    lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteRVAdapter: FavoriteRVAdapter // 변수 선언
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
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        // favoriteRVAdapter 초기화
        favoriteRVAdapter = FavoriteRVAdapter(ArrayList()) // stores를 비어있는 리스트로 초기화
        favoriteRVAdapter.setMyItemClickListener(this)
        binding.favoriteMainRecyclerView.adapter = favoriteRVAdapter

        val apiService = ApiClient.retrofitInterface
        val userId = 1 // 사용자 ID를 여기에 설정하세요

        apiService.getBookmarkedStores(userId).enqueue(object : Callback<List<Store>> {
            override fun onResponse(call: Call<List<Store>>, response: Response<List<Store>>) {
                if (response.isSuccessful) {
                    val stores = response.body()
                    stores?.let {
                        storeDatas.clear()
                        storeDatas.addAll(it)
                        favoriteRVAdapter.notifyDataSetChanged()
                        updateFavoriteCount()
//                        Toast.makeText(requireContext(), "북마크 성공", Toast.LENGTH_SHORT).show()

                    }
                } else {
                    // API 요청이 실패한 경우 처리할 내용을 여기에 추가하세요
                    Toast.makeText(requireContext(), "북마크 실패", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<List<Store>>, t: Throwable) {
                Toast.makeText(requireContext(),"실패",Toast.LENGTH_SHORT).show()
            }
        })

        favoriteRVAdapter = FavoriteRVAdapter(storeDatas)
        favoriteRVAdapter.setMyItemClickListener(this)
        binding.favoriteMainRecyclerView.apply {
            adapter = favoriteRVAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        updateFavoriteCount()

        return binding.root
    }

    private fun updateFavoriteCount() {
        val favoriteCount = favoriteRVAdapter.itemCount
        binding.favoriteSumTv.text = getString(R.string.favorite_sum, favoriteCount)
    }

    override fun onItemClick(store: Store) {
        // 아이템 클릭 시 StoreActivity를 시작
        val intent = Intent(requireContext(), StoreActivity::class.java)
        intent.putExtra("storeId", store.store_id ?: 0) // storeImg가 null이 아니면 해당 값, null이면 -1을 전달
        intent.putExtra("storeImg", store.image ?: "") // storeImg가 null이 아니면 해당 값, null이면 -1을 전달
        intent.putExtra("storeName", store.store_name ?: "")

        startActivity(intent)
    }

    override fun onStarClick(store: Store) {
        // 현재 북마크 상태 토글
        if (store.is_bookmarked == 1) store.is_bookmarked = 0 else store.is_bookmarked = 1

        // 서버에 북마크 상태 업데이트 요청 보내기
        val apiService = ApiClient.retrofitInterface
        val userId = 1 // 사용자 ID를 여기에 설정하세요

        apiService.changeBookmarkStatus(store.store_id, userId).enqueue(object : Callback<BookmarkResponse> {
            override fun onResponse(call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                if (response.isSuccessful) {
                    // 북마크 상태 변경 성공
                    updateStoreStarImage(store) // RecyclerView 업데이트
                    updateFavoriteCount() // 즐겨찾기 수 업데이트
                } else {
                    // 북마크 상태 변경 실패
                    Toast.makeText(requireContext(), "북마크 상태 변경 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                // 네트워크 오류 등으로 인한 실패
                Toast.makeText(requireContext(), "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        apiService.getBookmarkedStores(userId).enqueue(object : Callback<List<Store>> {
            override fun onResponse(call: Call<List<Store>>, response: Response<List<Store>>) {
                if (response.isSuccessful) {
                    val stores = response.body()
                    stores?.let {
                        storeDatas.clear()
                        storeDatas.addAll(it)
                        favoriteRVAdapter.notifyDataSetChanged()
                        updateFavoriteCount()
                    }
                } else {
                    // API 요청이 실패한 경우 처리할 내용을 여기에 추가하세요

                }
            }

            override fun onFailure(call: Call<List<Store>>, t: Throwable) {
                Toast.makeText(requireContext(),"실패",Toast.LENGTH_SHORT).show()
            }
        })

        val favoriteRVAdapter = FavoriteRVAdapter(storeDatas)
        favoriteRVAdapter.setMyItemClickListener(this)
        binding.favoriteMainRecyclerView.adapter = favoriteRVAdapter
    }



    private fun updateStoreStarImage(store: Store) {
        val adapter = binding.favoriteMainRecyclerView.adapter as? FavoriteRVAdapter
        adapter?.updateStoreStarImage(store)
    }
}