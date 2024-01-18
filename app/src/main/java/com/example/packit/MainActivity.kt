package com.example.packit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.packit.databinding.ActivityMainBinding
import com.example.packit.databinding.FragmentStoreListBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dialog: BottomSheetDialog
    private lateinit var bottomSheet: FragmentStoreListBinding
    private var storeDatas = ArrayList<Store>()

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            showBottomSheet()

            storeDatas.apply{
                add(Store(1, "가게 이름", "address 1", "영업 중","평점 4.5", R.drawable.store_img_1))
                add(Store(2, "옥루", "address 2", "영업 종료","평점 4.5",R.drawable.store_img_2))
                add(Store(3, "선식당", "address 3", "영업 중","평점 4.5", R.drawable.store_img_3))
                add(Store(4, "가게 이름", "address 1", "영업 중","평점 4.5", R.drawable.store_img_1))
                add(Store(5, "옥루", "address 2", "영업 종료","평점 4.5",R.drawable.store_img_2))
                add(Store(6, "선식당", "address 3", "영업 중","평점 4.5", R.drawable.store_img_3))

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
        bottomSheet.storeListRecyclerView.adapter = storeListRVAdapter
        dialog.show()

    }

}