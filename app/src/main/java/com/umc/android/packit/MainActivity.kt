package com.umc.android.packit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc.android.packit.databinding.ActivityMainBinding
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.room.Index

private const val TAG_MAP = "map_fragment"
private const val TAG_FAVORITE = "favorite_fragment"


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fl_container) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomnavi.setupWithNavController(navController)
         if (savedInstanceState == null) {
             supportFragmentManager.beginTransaction()
                 .replace(R.id.container, MapFragment())
                 .commit()
         }

        binding.bottomnavi.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.mapFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MapFragment())
                        .commit()
                }

                R.id.favoriteFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, FavoriteFragment())
                        .commit()
                }

           /*     R.id.myPackitFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, myPackitFragment() )
                        .commit()
                }*/

                R.id.orderHistoryFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, OrderHistoryFragment())
                        .commit()
                }

                R.id.myInfoFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MyInfoFragment())
                        .commit()
                }


            /*    R.id.favoriteFragment -> {
                    // If you have a destination with id "favoriteFragment" in your navigation graph
                    navController.navigate(R.id.favoriteFragment)
                }*/

            }
            true
        }

    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.fl_container, fragment, tag)
        }

        val favorite= manager.findFragmentByTag(TAG_FAVORITE)

        if (favorite != null){
            fragTransaction.hide(favorite)
        }

        if (tag == TAG_FAVORITE) {
            if (favorite!=null){
                fragTransaction.show(favorite)
            }
        }


        /*if (home != null){
            fragTransaction.hide(home)
        }

        if (myPage != null) {
            fragTransaction.hide(myPage)
        }*/

        /*
                if (tag == TAG_CALENDER) {
                    if (calender!=null){
                        fragTransaction.show(calender)
                    }
                }
                else if (tag == TAG_HOME) {
                    if (home != null) {
                        fragTransaction.show(home)
                    }
                }

                else if (tag == TAG_MY_PAGE){
                    if (myPage != null){
                        fragTransaction.show(myPage)
                    }
                }
        */
        fragTransaction.commitAllowingStateLoss()
    }
}

