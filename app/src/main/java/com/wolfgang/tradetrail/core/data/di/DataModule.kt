package com.wolfgang.tradetrail.core.data.di

import com.wolfgang.tradetrail.core.data.repository.ProductRepoImpl
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
        productRepoImpl: ProductRepoImpl
    ): ProductRepository
}
