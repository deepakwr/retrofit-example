package com.deepak.retrofitexample

import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceHolderApi {

    @GET("posts")
    fun getPosts():Call<List<Post>>

    @GET("posts")
    fun getPosts(@Query("userId") userid:Int):Call<List<Post>>

    @GET("posts")
    fun getPosts(@Query("userId") userid:Int?,@Query("_sort") sort:String?,@Query("_order") order:String?):Call<List<Post>>

    @GET("posts")
    fun getPosts(@Query("userId") userid:Array<Int>,@Query("_sort") sort:String?,@Query("_order") order:String?):Call<List<Post>>

    @GET("posts")
    fun getPosts(@QueryMap() parameters:Map<String,String>):Call<List<Post>>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") postId:Int):Call<List<Comment>>

    @GET
    fun getComments(@Url url:String):Call<List<Comment>>

    @POST("posts")
    fun createPost(@Body post:Post):Call<Post>

    @FormUrlEncoded
    @POST("posts")
    fun createPost(@Field("userId") userId:Int,@Field("title") title:String?, @Field("body") text:String?):Call<Post>

    @FormUrlEncoded
    @POST("posts")
    fun createPost(@FieldMap() parameters:Map<String,String>):Call<Post>

    @PUT("posts/{id}")
    fun putPost(@Path("id") id:Int,@Body post:Post):Call<Post>

    @Headers("Static-header1: 123","Static-header2: 456")
    @PUT("posts/{id}")
    fun putPostWithStaticHeader(@Path("id") id:Int,@Body post:Post):Call<Post>

    @Headers("Static-header1: 123","Static-header2: 456")
    @PUT("posts/{id}")
    fun putPostWithDynamicHeader(@Header("Dynamic-header") header:String,@Path("id") id:Int, @Body post:Post):Call<Post>

    @PATCH("posts/{id}")
    fun patchPost(@Path("id") id:Int,@Body post:Post):Call<Post>

    @PATCH("posts/{id}")
    fun patchPostWithHeaderMap(@HeaderMap headers:Map<String,String> ,@Path("id") id:Int, @Body post:Post):Call<Post>

    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id:Int):Call<Void>

}