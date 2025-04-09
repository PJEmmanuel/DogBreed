package com.example.dogbreed.data.respository

import com.example.dogbreed.data.mapper.toDomainDetails
import com.example.dogbreed.data.mapper.toDomainList
import com.example.dogbreed.data.remote.DogApiService
import com.example.dogbreed.domain.model.DogBreedDetails
import com.example.dogbreed.domain.model.DogBreedsList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OnlineDogBreedRepository(private val dogApi: DogApiService) : DogBreedRepository {

    //Llama al servidor
    override suspend fun getBreedsList(): Result<List<DogBreedsList>> =
        withContext(Dispatchers.IO) {
            try {
                val response = dogApi.getDogBreedsList().map { it.toDomainList() }//Mappper
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    //Llama al servidor con la referencia por parámetro para los detalles
    override suspend fun getBreedDetails(referenceImageId: String): Result<DogBreedDetails> =
        withContext(Dispatchers.IO) {
            try {
                val response = dogApi.getDogBreedDetails(referenceImageId)
                val breedDetails = response.breeds.firstOrNull()
                    ?.toDomainDetails(response.url)
                    ?: return@withContext Result.failure(Exception("No breed details found"))
                Result.success(breedDetails)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}