package com.android.joshuamarotta.flickrado.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.android.joshuamarotta.flickrado.api.response.OfflineFlickrResponse
import com.android.joshuamarotta.flickrado.database.dao.FlickrDao
import com.android.joshuamarotta.flickrado.database.typeConverters.OfflineFlickrResponseConverter
import kotlinx.coroutines.CoroutineScope

@TypeConverters(OfflineFlickrResponseConverter::class)
@Database(entities = [OfflineFlickrResponse::class], version = 1)
abstract class FlickrDatabase: RoomDatabase() {

    abstract fun flickrDao(): FlickrDao

    companion object {
        @Volatile
        private var INSTANCE: FlickrDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): FlickrDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlickrDatabase::class.java,
                    "flickr_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}