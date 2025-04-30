package com.wolfgang.tradetrail.core.data.di

import com.wolfgang.tradetrail.core.data.api.TradeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideOkHttp() = OkHttpClient.Builder().build()

    @Provides
    fun provideRetrofit(ok: OkHttpClient) = Retrofit.Builder()
        .baseUrl("https://dummyjson.com")
//        .baseUrl("https://fakestoreapi.com/")
        .client(ok)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideApi(r: Retrofit) = r.create(TradeApi::class.java)
}
