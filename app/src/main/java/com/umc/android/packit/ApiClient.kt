package com.umc.android.packit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://port-0-pack-it-ghdys32bls1g7ot3.sel5.cloudtype.app" // API의 기본 URL을 여기에 입력하세요
    private var retrofit: Retrofit? = null

    val retrofitInterface: RetrofitInterface
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(RetrofitInterface::class.java)
        }
}
