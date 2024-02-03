package com.umc.android.packit

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.android.packit.databinding.ActivityMenuInfoBinding
import com.umc.android.packit.databinding.ActivityStoreBinding

class MenuInfoActivity: AppCompatActivity() {
    lateinit var binding : ActivityMenuInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val menu = intent.getSerializableExtra("MenuData") as? Menu
        binding.menuInfoImageIv.setImageResource(menu?.menuImg!!)
        binding.menuInfoNameTv.text = menu.menu_name
        binding.menuInfoDescriptionTv.text = menu.about_menu
        binding.menuInfoPrice02Tv.text = menu.price.toString()+"원"
        binding.menuInfoSize02Tv.text = menu.containers
        binding.menuInfoCautionMessageTv.text = menu.notice



    }

}