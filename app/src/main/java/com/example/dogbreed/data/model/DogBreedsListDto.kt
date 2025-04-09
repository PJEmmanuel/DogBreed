package com.example.dogbreed.data.model

import com.google.gson.annotations.SerializedName

data class DogBreedsListResponse(
    val breeds: List<DogBreedsListDto>
)

data class DogBreedsListDto(
    val id: String,
    val name: String,
    @SerializedName(value = "reference_image_id") val referenceImageId: String?,
)