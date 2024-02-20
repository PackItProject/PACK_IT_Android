package com.umc.android.packit
import retrofit2.Call
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    // 장바구니 메뉴 추가
    @POST("/cart")
    fun addMenuToCart(@Body cartItem: CartItem): Call<BookmarkResponse>
    // 원래 AddToCartResponse라는 데이터 클래스 만들어야 겠지만, 같은 매개변수 사용하니까 일단은 BookmarkResponse 사용해봄

    // 장바구니 메뉴 삭제
    // DELETE는 원래 @Body 불가로, HTTP 사용
    @HTTP(method = "DELETE", path = "/cart", hasBody = true)
    fun subMenuToCart(@Body deleteCartRequest: DeleteCartRequest): Call<BookmarkResponse>

    // 장바구니 조회
    @GET("/cart")
    fun getCartMenus(
        @Query("pk_user") userId: Int,
        @Query("store_id") storeId: Int,
        ): Call<List<CartResponse>>

    @GET("/order/{store_id}/grade")
    fun getStoreReviews(@Path("store_id") storeId: Int): Call<List<Grade>>

    @GET("/order/{store_id}/info")
    fun getStoreInfo(@Path("store_id") storeId: Int): Call<List<StoreInfo>>

    @GET("/order/near/{store_id}")
    fun getStoreMenus(@Path("store_id") storeId: Int): Call<List<Menu>>
//    @GET("/cart/order/{pk_user}")
//    fun getOrderLists(@Path("pk_user") userId: Int): Call<List<OrderHistoryMenu>>

    @GET("/order/near/{store_id}/menu/{menu_id}")
    fun getMenuInfo(
        @Path("store_id") storeId: Int,
        @Path("menu_id") menuId: Int
    ): Call<List<Menu>>

    // 가게 이름으로 가게 정보 가져오기
    @GET("/order/search/{store_name}")
    fun getStoreByName(@Path("store_name") storeName: String): Call<List<StoreResponse>>

    @GET("/cart/order/{pk_user}")
    suspend fun getOrderLists(@Path("pk_user") userId: Int): Response<List<OrderHistoryMenu>>

    @GET("/cart/orderdetail/{orderId}")
    suspend fun getOrderDetail(@Path("orderId") orderId: Int): Response<List<OrderHistoryDetail>>

    @POST("auth/kakao/signin")
    suspend fun sendUserInfo(@Body request: LoginRequest): Response<SignInResponse>

    @POST("/cart/order")//주문추가
    fun addOrder(@Body order: OrderRequest): Call<AddOrderResponse>

    @DELETE("/cart/order/{order_id}") //주문삭제
    fun deleteOrder(@Path("order_id") orderId: Int): Call<DeleteOrderResponse>
}


