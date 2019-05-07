package com.android.joshuamarotta.flickrado.repositories

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import android.util.Log
import com.android.joshuamarotta.flickrado.api.FlickrApi
import com.android.joshuamarotta.flickrado.api.response.FlickrResponse
import com.android.joshuamarotta.flickrado.api.response.OfflineFlickrResponse
import com.android.joshuamarotta.flickrado.database.dao.FlickrDao
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject

const val TAGS = "tags"
const val COLORADO_MOUNTAINS = "coloradomountains"
const val FORMAT = "format"
const val JSON = "json"
const val NO_JSON_CALLBACK = "nojsoncallback"
const val ONE = "1"

class MainRepository @Inject constructor(val scope: CoroutineScope, val flickrApi: FlickrApi, private val flickrDao: FlickrDao) {
    private val TAG = this::class.java.canonicalName

    private val compositeDisposable = CompositeDisposable()
    val flickrItems: LiveData<OfflineFlickrResponse> = flickrDao.getFlickrResponse()

    init {
            flickrApi.getFlickrItems("services/feeds/photos_public.gne", mapOf(TAGS to COLORADO_MOUNTAINS, FORMAT to JSON, NO_JSON_CALLBACK to ONE))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap { Observable.just(it) }
                .subscribe(object: Observer<FlickrResponse> {
                    override fun onComplete() { }
                    override fun onSubscribe(d: Disposable) { compositeDisposable.add(d) }
                    override fun onNext(response: FlickrResponse) {
                        if (databaseContainsFlickrResponse(response)) {
                            insertToDatabase(
                                OfflineFlickrResponse(
                                    description = response.description,
                                    generator = response.generator,
                                    items = response.items.toMutableList(),
                                    link = response.link,
                                    modified = response.modified,
                                    title = response.title
                                )
                            )
                        }
                    }
                    override fun onError(e: Throwable) { Log.d(TAG, "RETROFIT ERROR") }
                })
    }

    private fun databaseContainsFlickrResponse(it: FlickrResponse) = flickrItems.value?.items?.containsAll(it.items) != true

    private fun insertToDatabase(offlineFlickrResponse: OfflineFlickrResponse) = scope.launch(Dispatchers.IO) { insert(offlineFlickrResponse) }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(offlineFlickrResponse: OfflineFlickrResponse) { flickrDao.insert(offlineFlickrResponse) }

    fun onCleared() {
        compositeDisposable.clear()
        scope.coroutineContext.cancel()
    }
}