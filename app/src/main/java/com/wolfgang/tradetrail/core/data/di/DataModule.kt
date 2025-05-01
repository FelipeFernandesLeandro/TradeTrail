package com.wolfgang.tradetrail.core.data.di

import com.wolfgang.tradetrail.core.data.repository.AuthRepository
import com.wolfgang.tradetrail.core.data.repository.AuthRepositoryImpl
import com.wolfgang.tradetrail.core.data.repository.CartRepository
import com.wolfgang.tradetrail.core.data.repository.CartRepositoryImpl
import com.wolfgang.tradetrail.core.data.repository.ProductRepositoryImpl
import com.wolfgang.tradetrail.core.data.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindProductRepo(
        productRepoImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    abstract fun bindCartRepo(
        cartRepoImpl: CartRepositoryImpl
    ): CartRepository

    @Binds
    abstract fun bindAuthRepo(
        authRepoImpl: AuthRepositoryImpl
    ): AuthRepository
}
