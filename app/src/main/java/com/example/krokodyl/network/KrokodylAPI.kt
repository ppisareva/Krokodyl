package com.example.krokodyl.network

import com.example.krokodyl.model.CategoryFromAPI
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
// todo update the data only if database is empty
private const val BASE_URL = "https://f864e356-86a0-4caa-9c53-c1d6bdbfbea3.mock.pstmn.io/"
//private const val BASE_URL = "https://13225fdf-167b-40b2-97dd-c8a2ba523242.mock.pstmn.io/"

private val moshi= Moshi.Builder()
    .add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface KrokodylAPIService {
    @GET("categories")
    fun getCategoriesList(): Deferred<List<CategoryFromAPI>>

    @GET("words")
    fun getWordsListByCategory(@Query("category") type: String): Deferred<List<String>>
}

object KrokodylAPI {
    val retrofitService : KrokodylAPIService by lazy {
        retrofit.create(KrokodylAPIService::class.java)
    }
}
