package com.android.joshuamarotta.flickrado

import android.arch.persistence.room.Room
import android.support.test.runner.AndroidJUnitRunner
import com.android.joshuamarotta.flickrado.api.FlickrApi
import com.android.joshuamarotta.flickrado.application.FrameworkApplication
import com.android.joshuamarotta.flickrado.database.FlickrDatabase
import com.android.joshuamarotta.flickrado.database.dao.FlickrDao
import com.android.joshuamarotta.flickrado.repositories.MainRepository
import com.android.joshuamarotta.flickrado.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.inject
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(JUnit4::class)
class InjectionTest : KoinTest, AndroidJUnitRunner() {

    val viewModel by inject<MainViewModel>()
    val mainRepository by inject<MainRepository>()
    val flickrDao by inject<FlickrDao>()

    lateinit var retrofit: Retrofit
    lateinit var flickrApi: FlickrApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        fun buildRetrofit() =
            Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        retrofit = buildRetrofit()

        flickrApi = retrofit.create(FlickrApi::class.java)

        val flickrApiModule = module {
            single { flickrApi }
        }

        val contextModule = module {
            single { Job() }
            single { CoroutineScope(get() as Job + Dispatchers.Main) }
        }

        val repositoryModule = module {
            single { Room.inMemoryDatabaseBuilder(get(), FlickrDatabase::class.java)
                .allowMainThreadQueries()
                .build().flickrDao()
            }
            single { MainRepository(flickrApi = get(), flickrDao = get(), scope = get()) }
        }


        startKoin {
            androidContext(mock(FrameworkApplication::class.java))
            modules(
                contextModule,
                repositoryModule,
                module { viewModel { MainViewModel(application = androidApplication(), mainRepository = get()) } },
                flickrApiModule
            )
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `Test injection setup`() {
        getKoin().checkModules()
    }

    @Test
    fun `Test Retrofit inject`() {
        assertNotNull(retrofit)
    }

    @Test
    fun `Test ViewModel inject`() {
        assertNotNull(viewModel)
    }

    @Test
    fun `Test MainRepository inject`() {
        assertNotNull(mainRepository)
    }

    @Test
    fun `Test FlickrDao inject`() {
        assertNotNull(flickrDao)
    }
}



