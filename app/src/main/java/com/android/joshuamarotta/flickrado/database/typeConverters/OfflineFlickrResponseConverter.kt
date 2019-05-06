package com.android.joshuamarotta.flickrado.database.typeConverters

import android.arch.persistence.room.TypeConverter
import com.android.joshuamarotta.flickrado.api.response.FlickrItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class OfflineFlickrResponseConverter {

    @TypeConverter
    fun fromFlickrItemList(flickrItems: MutableList<FlickrItem> ): String {
        val gson = Gson()
        val type = object : TypeToken<MutableList<FlickrItem>>() {

        }.type
        return gson.toJson(flickrItems, type)
    }

    @TypeConverter
    fun toCountryLangList(flickrItemsString: String): MutableList<FlickrItem> {
        val gson = Gson()
        val type = object : TypeToken<MutableList<FlickrItem>>() {

        }.type
        return gson.fromJson(flickrItemsString, type)
    }
}