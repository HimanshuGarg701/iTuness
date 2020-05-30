package com.example.ituness

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://itunes.apple.com/"

private val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface SongApiService{
    @GET("search/?term=honey+singh")
    fun getSongs() : Deferred<ReturnedData>
}

object SongsApi{
    val retrofitService : SongApiService by lazy {
        retrofit.create(SongApiService::class.java)
    }
}
