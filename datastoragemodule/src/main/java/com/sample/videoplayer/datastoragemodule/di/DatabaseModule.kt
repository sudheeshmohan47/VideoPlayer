package com.sample.videoplayer.datastoragemodule.di

/*import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton*/

/*
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): OnlineStoreAppDatabase {
        return Room.databaseBuilder(context, OnlineStoreAppDatabase::class.java, "online_store_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideWishlistDao(database: OnlineStoreAppDatabase): WishlistDao {
        return database.wishlistDao()
    }

    @Provides
    fun provideCartDao(database: OnlineStoreAppDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    fun provideSelectedCategoryDao(database: OnlineStoreAppDatabase): SelectedCategoryDao {
        return database.selectedCategoryDao()
    }
}
*/
