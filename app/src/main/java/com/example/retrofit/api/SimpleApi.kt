package com.example.retrofit.api
import com.example.retrofit.model.jsonBase
import com.example.retrofit.model.profile
import com.example.retrofit.util.Constants
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*
import java.util.concurrent.TimeUnit

interface SimpleApi {


    @GET("api/profile")
    fun getUserProfile(): Call<profile>

    @GET("api/users?page=2")
    fun getPostGrid(): Call<jsonBase>

    @GET("api/users?page=2")
    fun getPost(): Call<jsonBase>

    @POST("api/updateprofile")
    @Multipart
    fun updateUserProfile(
        @Part file: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("gender") gender: RequestBody

    ): Call<profile>


}

