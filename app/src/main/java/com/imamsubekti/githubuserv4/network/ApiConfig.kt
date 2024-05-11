package com.imamsubekti.githubuserv4.network

import com.imamsubekti.githubuserv4.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val client = OkHttpClient.Builder().run {
                addInterceptor { chain ->
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

                    val request = chain
                        .request()
                        .newBuilder()
                        .addHeader("Authorization", "token ${BuildConfig.API_KEY}")
                        .build()

                    chain.proceed(request)

                }
                build()
            }

            val retrofit = Retrofit.Builder().run {
                baseUrl(BuildConfig.BASE_URL)
                addConverterFactory(GsonConverterFactory.create())
                client(client)
                build()
            }

            return retrofit.create(ApiService::class.java)
        }
    }
}