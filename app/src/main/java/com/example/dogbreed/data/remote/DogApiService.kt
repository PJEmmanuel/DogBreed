package com.example.dogbreed.data.remote

import com.example.dogbreed.data.model.DogBreedDetailsResponse
import com.example.dogbreed.data.model.DogBreedsDetailsDto
import com.example.dogbreed.data.model.DogBreedsListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {

    // Lista de razas
    @GET("breeds")
    suspend fun getDogBreedsList(): List<DogBreedsListDto>

    // Detalles
    @GET("images/{referenceImageId}")
    suspend fun getDogBreedDetails(@Path("referenceImageId") referenceImageId: String)
    : DogBreedDetailsResponse
}