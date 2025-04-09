package com.example.dogbreed.data.respository

import com.example.dogbreed.domain.model.DogBreedDetails
import com.example.dogbreed.domain.model.DogBreedsList

interface DogBreedRepository {
    suspend fun getBreedsList(): Result<List<DogBreedsList>>
    suspend fun getBreedDetails(referenceImageId: String): Result<DogBreedDetails>
}