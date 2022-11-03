package com.oriadesoftdev.retrofitrxjava3.data.remote

import com.oriadesoftdev.retrofitrxjava3.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object Networking {

    private const val NETWORKING_TIMEOUT = 60L

    fun create(baseUrl: String, cacheDir: File, cacheSize: Long): NetworkService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .cache(Cache(cacheDir, cacheSize))
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = if (BuildConfig.DEBUG)
                                HttpLoggingInterceptor.Level.BODY
                            else HttpLoggingInterceptor.Level.NONE
                        }
                    )
                    .readTimeout(NETWORKING_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(NETWORKING_TIMEOUT, TimeUnit.SECONDS)
                    .build()
            )
            .build()
            .create(NetworkService::class.java)
}