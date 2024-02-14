package com.umc.android.packit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitInterface {
    @GET("/bookmark/{pk_user}")
    fun getBookmarkedStores(@Path("pk_user") userId: Int): Call<List<Store>>

    @POST("/bookmark/{store_id}/{pk_user}")
    fun changeBookmarkStatus(
        @Path("store_id") storeId: Int,
        @Path("pk_user") userId: Int
    ): Call<BookmarkResponse>
}