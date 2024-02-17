package com.umc.android.packit
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
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

    // 장바구니에 메뉴 추가
    @POST("/cart")
    fun addMenuToCart(@Body cartItem: CartItem): Call<BookmarkResponse>
    // @Body request: AddToCartRequest의 AddToCartRequest라는 데이터 클래스 만들까 고민중
    // 원래 AddToCartResponse라는 데이터 클래스 만들어야 겠지만, 같은 매개변수 사용하니까 일단은 BookmarkResponse 사용해봄

    // 장바구니 조회
}