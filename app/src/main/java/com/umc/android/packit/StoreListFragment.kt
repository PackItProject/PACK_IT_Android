package com.umc.android.packit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.android.packit.databinding.FragmentStoreListBinding

class StoreListFragment : Fragment() {

    private var storeDatas = ArrayList<Store>()
    lateinit var binding: FragmentStoreListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreListBinding.inflate(inflater, container, false)

        storeDatas.apply{
            add(Store(1, "name 1", "address 1", "영업 종료","Rate 1", R.drawable.store_img_1))
            add(Store(2, "name 2", "address 2", "영업 종료","Rate 2",R.drawable.store_img_2))
            add(Store(3, "name 3", "address 3", "영업 중","Rate 3", R.drawable.store_img_3))
        }

        val storeListRVAdapter = StoreListRVAdapter(storeDatas)
        binding.storeListRecyclerView.adapter = storeListRVAdapter
        binding.storeListRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        storeListRVAdapter.setMyItemClickListener(object : StoreListRVAdapter.MyItemClickListener {
            override fun onItemClick(store: Store) {
                // 아이템 클릭 시 동작 정의
                Toast.makeText(context, "Clicked on ${store.name}", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }
//
//    override fun onStart() {
//        super.onStart()
//        addExampleStores()
//        initRecyclerview()
//    }
//
//    private fun initRecyclerview() {
//        binding.storeListRecyclerView.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//
//        val storeListRVAdapter = StoreListRVAdapter(storeDatas)
//        storeListRVAdapter.setMyItemClickListener(object : StoreListRVAdapter.MyItemClickListener {
//            override fun onItemClick(store: Store) {
//                // 아이템 클릭 시 동작 정의
//                Toast.makeText(context, "Clicked on ${store.name}", Toast.LENGTH_SHORT).show()
//            }
//        })
//
//        binding.storeListRecyclerView.adapter = storeListRVAdapter
//    }
//
//    // 예시 데이터를 추가하는 함수
//    private fun addExampleStores() {
//        // RecyclerView 어댑터에 데이터 추가
//        storeDatas.apply{
//            add(Store(1, "name 1", "address 1", "영업 종료","Rate 1", R.drawable.store_img_1))
//            add(Store(2, "name 2", "address 2", "영업 종료","Rate 2",R.drawable.store_img_2))
//            add(Store(3, "name 3", "address 3", "영업 중","Rate 3", R.drawable.store_img_3))
//        }
//
//        // 어댑터에 변경된 데이터 적용
//        (binding.storeListRecyclerView.adapter as? StoreListRVAdapter)?.addStores(storeDatas)
//        Log.e("StoreListFragment", "Item count after adding example stores: ${binding.storeListRecyclerView.adapter?.itemCount}")
//        Log.e("StoreListFragment", "Adapter is null: ${binding.storeListRecyclerView.adapter == null}")
//
//    }
}
