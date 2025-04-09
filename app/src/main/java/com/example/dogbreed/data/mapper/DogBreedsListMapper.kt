package com.example.dogbreed.data.mapper

import com.example.dogbreed.data.model.DogBreedsListDto
import com.example.dogbreed.domain.model.DogBreedsList

fun DogBreedsListDto.toDomainList(): DogBreedsList {
    return DogBreedsList(
        id = this.id,
        name = this.name,
        referenceImageId = referenceImageId ?: "",
    )
}