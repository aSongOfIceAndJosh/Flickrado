package com.android.joshuamarotta.flickrado.dagger.module

import android.content.Context
import com.android.joshuamarotta.flickrado.adapters.MainRecyclerViewAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AdapterModule {
    @Singleton
    @Provides
    fun provideMainRecyclerViewAdapter(context: Context) = MainRecyclerViewAdapter(context)
}