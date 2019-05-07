package com.android.joshuamarotta.flickrado.dagger.module

import android.arch.persistence.room.Room
import com.android.joshuamarotta.flickrado.api.FlickrApi
import com.android.joshuamarotta.flickrado.application.FrameworkApplication
import com.android.joshuamarotta.flickrado.database.FlickrDatabase
import com.android.joshuamarotta.flickrado.database.dao.FlickrDao
import com.android.joshuamarotta.flickrado.repositories.MainRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class,
        CoroutineModule::class
    ]
)
class RoomModule {

    @Singleton
    @Provides
    fun provideFlickrDatabase(frameworkApplication: FrameworkApplication) : FlickrDatabase =
        Room.databaseBuilder(frameworkApplication, FlickrDatabase::class.java, "flickr_database").build()//flickrDatabase

    @Singleton
    @Provides
    fun provideFlickrDao(flickrDatabase: FlickrDatabase) = flickrDatabase.flickrDao()

    @Singleton
    @Provides
    fun provideFlickrApi(retrofit: Retrofit) = retrofit.create(FlickrApi::class.java)

    @Singleton
    @Provides
    fun provideRepository(scope: CoroutineScope, flickrApi: FlickrApi, flickrDao: FlickrDao) = MainRepository(scope = scope, flickrApi = flickrApi, flickrDao = flickrDao)
}