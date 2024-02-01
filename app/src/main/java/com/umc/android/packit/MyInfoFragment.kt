package com.umc.android.packit

import android.graphics.Outline
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.Button
import android.widget.ImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclas.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    companion object {
        const val TAG: String = "로그"

        fun newInstance(): MyInfoFragment {
            return MyInfoFragment()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val rootView = inflater.inflate(R.layout.fragment_my_info, container, false) // Replace with your fragment layout XML file

        val profileImageView: ImageView = rootView.findViewById(R.id.profile_img_iv)
        val favoriteButton: Button = rootView.findViewById(R.id.favorite_btn)
        val myRateButton: Button = rootView.findViewById(R.id.myRate_btn)

        // Set the ViewOutlineProvider for rounded corners
        profileImageView.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                val cornerRadius = resources.getDimensionPixelSize(R.dimen.corner_radius)
                outline.setRoundRect(0, 0, view.width, view.height, cornerRadius.toFloat())
            }
        }

        profileImageView.clipToOutline = true


        favoriteButton.setOnClickListener {
            // 즐겨찾기 버튼을 클릭했을 때 FavoriteFragment로 이동하는 코드
            navigateToFavoriteFragment()
        }

        myRateButton.setOnClickListener {
            // 내 평점 버튼을 클릭했을 때 RateFragment로 이동하는 코드
            navigateToRateFragment()
        }

        return rootView

    }
    private fun navigateToRateFragment() {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val rateFragment = RateFragment.newInstance()

        // Replace the current fragment with RateFragment
        fragmentTransaction.replace(R.id.fragment_container, rateFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    private fun navigateToFavoriteFragment() {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val favoriteFragment = FavoriteFragment.newInstance()

        // Replace the current fragment with FavoriteFragment
        fragmentTransaction.replace(R.id.fragment_container, favoriteFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


}