package com.simplesoftware.pokedexdabia.application

import android.app.Application
import com.bumptech.glide.Glide
import com.simplesoftware.pokedexdabia.domain.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(AppModule.modules)
        }
        Glide.get(this)
    }
}