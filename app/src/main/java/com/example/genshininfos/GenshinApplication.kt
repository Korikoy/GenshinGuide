package com.example.genshininfos

import android.app.Application
import com.example.genshininfos.di.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class GenshinApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(loginModule)
        }
    }

}