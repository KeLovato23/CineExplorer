package com.idigitalstudios.cineexplorerapp.di

import android.content.Context
import androidx.room.Room
import com.idigitalstudios.cineexplorerapp.data.local.AppDatabase
import com.idigitalstudios.cineexplorerapp.data.local.ShowDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "cineexplorer.db").build()

    @Provides
    @Singleton
    fun provideShowDao(db: AppDatabase): ShowDao = db.showDao()
}
