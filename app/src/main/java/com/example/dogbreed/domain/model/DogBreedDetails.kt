package com.example.dogbreed.domain.model

data class DogBreedDetails(
    val url: String,
    val name: String,
    val origin: String,
    val breedGroup: String?,
    val bredFor: String?,
    val lifeSpan: String?,
    val temperament: String?
)