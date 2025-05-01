package com.wolfgang.tradetrail.core.data.di

import com.wolfgang.tradetrail.core.data.remote.AuthApi
import com.wolfgang.tradetrail.core.data.remote.CartApi
import com.wolfgang.tradetrail.core.data.remote.ProductApi
import com.wolfgang.tradetrail.core.data.session.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideRetrofit(ok: OkHttpClient) = Retrofit.Builder()
        .baseUrl("https://dummyjson.com")
        .client(ok)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideOkHttp(auth: AuthInterceptor) =
        OkHttpClient.Builder().addInterceptor(auth).build()

    @Provides @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi =
        retrofit.create(ProductApi::class.java)

    @Provides @Singleton
    fun provideCartApi(retrofit: Retrofit): CartApi =
        retrofit.create(CartApi::class.java)
}
