package com.mj.coroutine_retrofit2.base

import android.app.Application
import com.mj.coroutine_retrofit2.viewModel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(viewModelModule)
        }
    }

    private val viewModelModule = module {
        viewModel { MainViewModel() }
    }
}