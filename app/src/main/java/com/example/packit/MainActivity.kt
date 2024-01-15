package com.example.packit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.packit.databinding.ActivityMainBinding
import com.example.packit.databinding.FragmentStoreListBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bottomSheet: FragmentStoreListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheet = FragmentStoreListBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bottomSheet.root)

//            val bottomSheetView = layoutInflater.inflate(R.layout.fragment_store_list, null)
//        bottomSheetDialog = BottomSheetDialog(this)
//            bottomSheetDialog.setContentView(bottomSheetView)

        binding.storeListBtn.setOnClickListener {
                bottomSheetDialog.show()
            }

        }

    }