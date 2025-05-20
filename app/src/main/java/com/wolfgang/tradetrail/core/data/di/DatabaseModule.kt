package com.wolfgang.tradetrail.core.data.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wolfgang.tradetrail.core.data.local.dao.CartDao
import com.wolfgang.tradetrail.core.data.local.entity.CartItemEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(entities = [CartItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "trade_trail.db")
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    fun provideCartDao(db: AppDatabase): CartDao = db.cartDao()
}