package com.simplesoftware.pokedexdabia.application

import android.app.Application
import com.bumptech.glide.Glide

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Glide.get(this)
    }
}