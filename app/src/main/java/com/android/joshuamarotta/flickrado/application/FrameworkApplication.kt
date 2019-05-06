package com.android.joshuamarotta.flickrado.application

import com.android.joshuamarotta.flickrado.dagger.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class FrameworkApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .application(this)
            .baseUrl("https://api.flickr.com/")
            .build()
    }
}