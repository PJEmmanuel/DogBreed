package com.example.dogbreed.domain.usecase

import com.example.dogbreed.data.respository.DogBreedRepository
import com.example.dogbreed.domain.model.DogBreedDetails
import com.example.dogbreed.domain.model.DogBreedsList

class GetDogBreedUseCase(private val repository: DogBreedRepository) {

    //LLamada desde el VM para la lista de razas
    suspend fun invokeBreedsList(): Result<List<DogBreedsList>> {
        return repository.getBreedsList()
    }

    //Llamada desde el VM para los detalles
    suspend fun invokeBreedDetails(referenceImageId: String): Result<DogBreedDetails> {
        return repository.getBreedDetails(referenceImageId)
    }

}