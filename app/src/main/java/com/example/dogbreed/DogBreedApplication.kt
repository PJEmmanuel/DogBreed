package com.example.dogbreed

import android.app.Application
import com.example.dogbreed.di.AppContainer
import com.example.dogbreed.di.AppDataContainer


class DogBreedApplication : Application() {

    lateinit var container : AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer()
    }
}