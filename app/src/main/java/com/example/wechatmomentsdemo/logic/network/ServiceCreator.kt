package com.example.wechatmomentsdemo.logic.network

import com.example.wechatmomentsdemo.extension.logD
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * 网络基础服务配置。
 *
 */
object ServiceCreator {

    private const val BASE_URL = "http://thoughtworks-ios.herokuapp.com"

    private val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                logD("http_request", message)
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY)

    val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10 * 1000, TimeUnit.MILLISECONDS)
        .writeTimeout(20 * 1000, TimeUnit.MILLISECONDS)
        .readTimeout(10 * 1000, TimeUnit.MILLISECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().registerTypeAdapterFactory(GsonTypeAdapterFactory()).create()))

    private val retrofit = builder.build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
}

