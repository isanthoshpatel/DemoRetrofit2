package com.example.demoretrofit2

import retrofit2.Call
import retrofit2.http.*

interface ApiCalls {

    @GET("posts")
    fun get(): Call<MutableList<Post>>

    @GET("posts")
    fun get(
        @Query("userId") userid: Int,
        @Query("_sort") sort: String,
        @Query("_order") order: String
    ): Call<MutableList<Post>>

    @GET("posts")
    fun get(@QueryMap map: MutableMap<Any, Any>): Call<MutableList<Post>>

    @GET("Posts/{id}/comments")
    fun get(@Path("id") id: Int): Call<MutableList<Commet>>

    @POST("posts")
    fun post(@Body post: Post): Call<Post>

    @POST("posts/{id}/comments")
    fun post(@Path("id") id:Int,
             @Body comment: Commet): Call<Commet>

    @PUT("posts/{id}")
    fun put(
        @Path("id") id: Int,
        @Body post: Post
    ): Call<Post>

    @PATCH("posts/{id}")
    fun patch(
        @Path("id") id: Int,
        @Body post: Post
    ): Call<Post>

    @DELETE("posts/{id}")
    fun delete(@Path("id") id:Int)


}