package com.example.dogbreed.data.model

import com.google.gson.annotations.SerializedName

data class DogBreedDetailsResponse(
    val url: String,
    val breeds: List<DogBreedsDetailsDto>
)

data class DogBreedsDetailsDto(
    val name: String,
    val origin: String?,
    val temperament: String?,
    @SerializedName(value = "breed_group") val breedGroup: String?,
    @SerializedName(value = "bred_for") val bredFor: String?,
    @SerializedName(value = "life_span") val lifeSpan: String?,
)
