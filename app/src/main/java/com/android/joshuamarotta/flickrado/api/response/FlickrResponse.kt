package com.android.joshuamarotta.flickrado.api.response

import android.arch.persistence.room.*

data class FlickrResponse(
    val description: String,
    val generator: String,
    val items: List<FlickrItem>,
    val link: String,
    val modified: String,
    val title: String
)

data class FlickrItem(
    val author: String,
    val author_id: String,
    val date_taken: String,
    val description: String,
    val link: String,
    val media: Media,
    val published: String,
    val tags: String,
    val title: String
)

val FlickrItem.formattedName: String
    get() = author.substringAfter(" ")
        .replace("\\", "")
        .replace("\"", "")
        .replace("(", "")
        .replace(")", "")


data class Media(val m: String)

@Entity(tableName = "flickr_response_table")
data class OfflineFlickrResponse(
    @PrimaryKey(autoGenerate = true)
    var flickrResponseId: Int = 0,
    val description: String,
    val generator: String,
    val items: MutableList<FlickrItem>,
    val link: String,
    val modified: String,
    val title: String
)