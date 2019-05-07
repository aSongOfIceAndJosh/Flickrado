package com.android.joshuamarotta.flickrado.dagger.module

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.*
import javax.inject.Singleton

@Module
class CoroutineModule {
    @Singleton
    @Provides
    fun provideJob() = Job()

    @Singleton
    @Provides
    fun provideMainCoroutineDispatcher() = Dispatchers.Main

    @Singleton
    @Provides
    fun provideCoroutineScope(job: Job, mainCoroutineDispatcher: MainCoroutineDispatcher) = CoroutineScope(job + mainCoroutineDispatcher)
}