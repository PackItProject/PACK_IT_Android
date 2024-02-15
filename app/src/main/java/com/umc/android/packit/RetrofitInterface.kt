package com.umc.android.packit
import retrofit2.Call

import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.DELETE
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



//    @GET("/cart/order/{pk_user}")
//    fun getOrderLists(@Path("pk_user") userId: Int): Call<List<OrderHistoryMenu>>


    @GET("/cart/order/{pk_user}")
    suspend fun getOrderLists(@Path("pk_user") userId: Int): Response<List<OrderHistoryMenu>>

    @POST("/cart/order")//주문추가
    fun addOrder(@Body order: OrderRequest): Call<AddOrderResponse>

    @DELETE("/cart/order/{order_id}") //주문삭제
    fun deleteOrder(@Path("order_id") orderId: Int): Call<DeleteOrderResponse>
}

