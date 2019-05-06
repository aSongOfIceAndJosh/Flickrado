package com.android.joshuamarotta.flickrado.api

import com.android.joshuamarotta.flickrado.api.response.FlickrResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface FlickrApi {
    @GET
    fun getFlickrItems(@Url url: String, @QueryMap parameters: Map<String, String>): Observable<FlickrResponse>
}