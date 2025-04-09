package com.example.dogbreed.ui.screens.details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.dogbreed.domain.model.DogBreedDetails
import com.example.dogbreed.ui.screens.DogUiState
import org.junit.Rule
import org.junit.Test

// Esta clase testea el estado DogUiState(Error, Loading, Success)
class DetailsBreedScreenTest {

    private val fakeDetailsFull = DogBreedDetails(
        "https://cdn2.thedogapi.com/images/BFRYBufpm.jpg",
        name = "Akita",
        origin = "London",
        breedGroup = "Working",
        bredFor = "Hunting",
        lifeSpan = "10 - 14 years",
        temperament = "Docile"
    )

    // breedGroup y temperament vacíos
    private val fakeDetailsNotFull = DogBreedDetails(
        "https://cdn2.thedogapi.com/images/BFRYBufpm.jpg",
        name = "Akita",
        origin = "London",
        breedGroup = "",
        bredFor = "Hunting",
        lifeSpan = "10 - 14 years",
        temperament = ""
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun detailsBreedScreen_verifyThatTheLoadingScreenIsDisplayed() {

        composeTestRule.setContent {
            DetailsBreedScreen(
                breedDetailsState = DogUiState.Loading,
                retryToFetchBreedDetails = {},
                navigateBack = {},
            )
        }

        // Buscar el elemento CircularProgressIndicator por su testTag
        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun detailsBreedScreen_verifyThatTheErrorScreenIsDisplayed() {
        composeTestRule.setContent {
            DetailsBreedScreen(
                breedDetailsState = DogUiState.Error,
                retryToFetchBreedDetails = {},
                navigateBack = {},
            )
        }
        // Buscar el elemento Button por su testTag
        composeTestRule.onNodeWithTag("button_reload_retryToFetchBreedDetails").assertIsDisplayed()
    }

    // Test si DogBreedsDetailsDto está completo
    @Test
    fun detailsBreedScreen_verifyThatTheSuccessScreenIsDisplayed() {
        composeTestRule.setContent {
            DetailsBreedScreen(
                breedDetailsState = DogUiState.Success(breedDetails = fakeDetailsFull),
                retryToFetchBreedDetails = {},
                navigateBack = {},
            )
        }

        composeTestRule.onNodeWithTag("dog_name").assertIsDisplayed()
        composeTestRule.onNodeWithTag("bred_for").assertIsDisplayed()
        composeTestRule.onNodeWithTag("breed_group").assertIsDisplayed()
        composeTestRule.onNodeWithTag("life_span").assertIsDisplayed()
        composeTestRule.onNodeWithTag("dog_temperament").assertIsDisplayed()
        composeTestRule.onNodeWithTag("dog_origin").assertIsDisplayed()

    }


    /* Test si DogBreedsDetailsDto NO está completo. BreedGroup y temperament vacíos y no aparecerán
     en pantalla. Si el servidor no contiene la información de algún elemento, no aparecerá en
     pantalla nada sobre el */
    @Test
    fun detailsBreedScreen_verifyThatTheSuccessScreenIsNotDisplayed() {
        composeTestRule.setContent {
            DetailsBreedScreen(
                breedDetailsState = DogUiState.Success(breedDetails = fakeDetailsNotFull),
                retryToFetchBreedDetails = {},
                navigateBack = {},
            )
        }

        // IsDisplayed
        composeTestRule.onNodeWithTag("dog_name").assertIsDisplayed()
        composeTestRule.onNodeWithTag("bred_for").assertIsDisplayed()
        composeTestRule.onNodeWithTag("life_span").assertIsDisplayed()
        composeTestRule.onNodeWithTag("dog_origin").assertIsDisplayed()

        //IsNotDisplayed
        composeTestRule.onNodeWithTag("breed_group").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("dog_temperament").assertIsNotDisplayed()

    }
}