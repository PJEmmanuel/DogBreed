package com.example.dogbreed.di

import com.example.dogbreed.data.remote.DogApi
import com.example.dogbreed.data.remote.DogApiService
import com.example.dogbreed.data.respository.DogBreedRepository
import com.example.dogbreed.data.respository.OnlineDogBreedRepository

interface AppContainer {
    val dogBreedRepository : DogBreedRepository
}

class AppDataContainer() : AppContainer{

    private val dogApi: DogApiService by lazy {
        DogApi.retrofitService
    }

    // Inyeccion dependencias
    override val dogBreedRepository: DogBreedRepository by lazy {
        OnlineDogBreedRepository(dogApi)
    }
}