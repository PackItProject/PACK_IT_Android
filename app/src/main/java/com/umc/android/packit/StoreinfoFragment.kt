package com.umc.android.packit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.android.packit.databinding.FragmentStoreinfoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StoreinfoFragment : Fragment() {

    private lateinit var binding: FragmentStoreinfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val storeId = arguments?.getInt("storeId", -1) ?: -1

        binding = FragmentStoreinfoBinding.inflate(inflater, container, false)

        val apiService = ApiClient.retrofitInterface

        apiService.getStoreInfo(storeId).enqueue(object : Callback<List<StoreInfo>> {
            override fun onResponse(call: Call<List<StoreInfo>>, response: Response<List<StoreInfo>>) {
                if (response.isSuccessful) {
                    val storeInfo = response.body()?.get(0)
                    // Populate UI with storeInfo
                    binding.storeinfoNameTv.text = storeInfo?.store_name
                    binding.storeinfoTelTv.text = "전화번호 :  "+storeInfo?.tel
                    binding.storeinfoAddressTv.text= "주소 : "+storeInfo?.address
                    binding.storeinfoRepresentTv.text = "대표자명 : "+storeInfo?.boss
                    binding.storeinfoNumTv.text = "사업자등록번호 :  "+storeInfo?.license
                    binding.storeinfoTimeOpenTv.text = storeInfo?.hours
                    binding.storeinfoIntroContentTv.text = storeInfo?.introduction
                    binding.storeinfoNoticeContentTv.text = storeInfo?.notice

                    // Populate other views similarly
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<List<StoreInfo>>, t: Throwable) {
                // Handle failure
            }
        })

        return binding.root
    }
}
