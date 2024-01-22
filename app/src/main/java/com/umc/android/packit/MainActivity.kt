package com.umc.android.packit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.umc.android.packit.databinding.ActivityMainBinding
import com.umc.android.packit.databinding.FragmentStoreListBinding
import com.umc.android.packit.databinding.ItemStoreListBinding

class MainActivity : AppCompatActivity(), StoreListRVAdapter.MyItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dialog: BottomSheetDialog
    private lateinit var bottomSheet: FragmentStoreListBinding
    private lateinit var itemStoreListBinding: ItemStoreListBinding
    private var storeDatas = ArrayList<Store>()

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            // showBottomSheet()

            storeDatas.apply{
                add(Store(1, "가게 이름", "address 1", "영업 중","평점 4.5",false, R.drawable.store_img_1))
                add(Store(2, "옥루", "address 2", "영업 종료","평점 4.5",false, R.drawable.store_img_2))
                add(Store(3, "선식당", "address 3", "영업 중","평점 4.5", true, R.drawable.store_img_3))
                add(Store(4, "가게 이름", "address 1", "영업 중","평점 4.5", false, R.drawable.store_img_1))
                add(Store(5, "옥루", "address 2", "영업 종료","평점 4.5",true,R.drawable.store_img_2))
                add(Store(6, "선식당", "address 3", "영업 중","평점 4.5", false,R.drawable.store_img_3))

            }

//            val bottomSheetView = layoutInflater.inflate(R.layout.fragment_store_list, null)
//        bottomSheetDialog = BottomSheetDialog(this)
//            bottomSheetDialog.setContentView(bottomSheetView)

            binding.storeListBtn.setOnClickListener {
                    showBottomSheet()
            }

        }

    private fun showBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.fragment_store_list, null)
        dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogView)
        bottomSheet = FragmentStoreListBinding.bind(dialogView)
        val storeListRVAdapter = StoreListRVAdapter(storeDatas)
        storeListRVAdapter.setMyItemClickListener(this)
        bottomSheet.storeListRecyclerView.adapter = storeListRVAdapter
        dialog.show()

    }

    override fun onItemClick(store: Store) {
        // 아이템 클릭 시 StoreActivity를 시작
        val intent = Intent(this, StoreActivity::class.java)
        startActivity(intent)
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.main_frm, StoreActivity())
//            .commitAllowingStateLoss()

        // BottomSheetDialog 닫기 (선택적)
        //dialog.dismiss()
    }

    override fun onStarClick(store: Store) {
        store.star = !store.star!! // Toggle the value
        updateStoreStarImage(store)
    }

    private fun updateStoreStarImage(store: Store) {
        val adapter = bottomSheet.storeListRecyclerView.adapter as? StoreListRVAdapter
        adapter?.updateStoreStarImage(store)
    }

}