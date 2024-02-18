package com.umc.android.packit
import retrofit2.Call
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitInterface {

    // 주변 가게 목록 조회
    @GET("/order/near")
    fun getNearbyStores(): Call<List<StoreResponse>>

    @GET("/bookmark/{pk_user}")
    fun getBookmarkedStores(@Path("pk_user") userId: Int): Call<List<Store>>

    @POST("/bookmark/{store_id}/{pk_user}")
    fun changeBookmarkStatus(
        @Path("store_id") storeId: Int,
        @Path("pk_user") userId: Int
    ): Call<BookmarkResponse>


    // 장바구니에 메뉴 추가
    @POST("/cart")
    fun addMenuToCart(@Body cartItem: CartItem): Call<BookmarkResponse>
    // @Body request: AddToCartRequest의 AddToCartRequest라는 데이터 클래스 만들까 고민중
    // 원래 AddToCartResponse라는 데이터 클래스 만들어야 겠지만, 같은 매개변수 사용하니까 일단은 BookmarkResponse 사용해봄

    // 장바구니 조회

    @GET("/order/{store_id}/grade")
    fun getStoreReviews(@Path("store_id") storeId: Int): Call<List<Grade>>

    @GET("/order/{store_id}/info")
    fun getStoreInfo(@Path("store_id") storeId: Int): Call<List<StoreInfo>>

    @GET("/order/near/{store_id}")
    fun getStoreMenus(@Path("store_id") storeId: Int): Call<List<Menu>>
//    @GET("/cart/order/{pk_user}")
//    fun getOrderLists(@Path("pk_user") userId: Int): Call<List<OrderHistoryMenu>>


    @GET("/cart/order/{pk_user}")
    suspend fun getOrderLists(@Path("pk_user") userId: Int): Response<List<OrderHistoryMenu>>

    @POST("/cart/order")//주문추가
    fun addOrder(@Body order: OrderRequest): Call<AddOrderResponse>

    @DELETE("/cart/order/{order_id}") //주문삭제
    fun deleteOrder(@Path("order_id") orderId: Int): Call<DeleteOrderResponse>
}


