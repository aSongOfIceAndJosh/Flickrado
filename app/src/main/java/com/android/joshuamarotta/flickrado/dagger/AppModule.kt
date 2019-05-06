package com.android.joshuamarotta.flickrado.dagger

import com.android.joshuamarotta.flickrado.application.FrameworkApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideContext(frameworkApplication: FrameworkApplication) = frameworkApplication.applicationContext
}