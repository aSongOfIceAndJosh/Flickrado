package com.android.joshuamarotta.flickrado.dagger.module

import com.android.joshuamarotta.flickrado.MainActivity
import com.android.joshuamarotta.flickrado.fragments.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindMainFragment(): MainFragment
}