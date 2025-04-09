package com.example.dogbreed.data.mapper

import com.example.dogbreed.data.model.DogBreedsDetailsDto
import com.example.dogbreed.domain.model.DogBreedDetails
import com.example.dogbreed.domain.model.DogBreedsList

fun DogBreedsDetailsDto.toDomainDetails(url:String): DogBreedDetails {
    return DogBreedDetails(
        name = this.name,
        origin = this.origin ?: "",
        breedGroup = this.breedGroup ?: "",
        bredFor = this.bredFor ?: "",
        lifeSpan = this.lifeSpan ?: "",
        temperament = this.temperament ?: "",
        url = url, // url está en DogBreedDetailsResponse. Aprovechamos el mapper y lo añadimos aquí
    )
}