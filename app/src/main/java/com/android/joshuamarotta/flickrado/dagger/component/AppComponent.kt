package com.android.joshuamarotta.flickrado.dagger.component

import com.android.joshuamarotta.flickrado.application.FrameworkApplication
import com.android.joshuamarotta.flickrado.dagger.AppModule
import com.android.joshuamarotta.flickrado.dagger.module.BuildersModule
import com.android.joshuamarotta.flickrado.dagger.module.RoomModule
import com.android.joshuamarotta.flickrado.dagger.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        BuildersModule::class,
        ViewModelModule::class,
        RoomModule::class
    ]
)
interface AppComponent : AndroidInjector<FrameworkApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: FrameworkApplication): Builder

        @BindsInstance
        fun baseUrl(@Named("baseUrl") baseUrl: String): Builder

        fun build(): AppComponent
    }
}