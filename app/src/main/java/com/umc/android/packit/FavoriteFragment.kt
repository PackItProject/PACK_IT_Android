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
                    }
                } else {
                    // API 요청이 실패한 경우 처리할 내용을 여기에 추가하세요

                }
            }

            override fun onFailure(call: Call<List<Store>>, t: Throwable) {
                Toast.makeText(requireContext(),"실패",Toast.LENGTH_SHORT).show()
            }
        })

//        storeDatas.apply{
//            add(Store(1, "가게 이름", "address 1", "영업 중","평점 4.5",true, R.drawable.store_img_1))
//            add(Store(2, "옥루", "address 2", "영업 종료","평점 4.5",false,R.drawable.store_img_2))
//            add(Store(3, "선식당", "address 3", "영업 중","평점 4.5", false,R.drawable.store_img_3))
//            add(Store(4, "가게 이름", "address 1", "영업 중","평점 4.5", true,R.drawable.store_img_1))
//            add(Store(5, "옥루", "address 2", "영업 종료","평점 4.5",false,R.drawable.store_img_2))
//            add(Store(6, "선식당", "address 3", "영업 중","평점 4.5",true, R.drawable.store_img_3))
//
//        }

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
        intent.putExtra("storeImg", store.storeImg ?: -1) // storeImg가 null이 아니면 해당 값, null이면 -1을 전달

        startActivity(intent)
    }

    override fun onStarClick(store: Store) {
        // 현재 북마크 상태 토글
        store.star = !(store.star)!!

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