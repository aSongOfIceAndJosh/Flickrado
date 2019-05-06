package com.android.joshuamarotta.flickrado.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.android.joshuamarotta.flickrado.api.response.OfflineFlickrResponse

@Dao
interface FlickrDao {
    @Query("SELECT * from flickr_response_table")
    fun getFlickrResponse(): LiveData<OfflineFlickrResponse>

    @Insert(onConflict = REPLACE)
    fun insert(offlineFlickrResponse: OfflineFlickrResponse)
}