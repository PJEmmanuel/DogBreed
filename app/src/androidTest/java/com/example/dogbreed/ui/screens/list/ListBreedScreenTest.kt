package com.example.dogbreed.ui.screens.list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.dogbreed.domain.model.DogBreedsList
import com.example.dogbreed.ui.screens.DogUiState
import org.junit.Rule
import org.junit.Test

// Esta clase testea el estado DogUiState(Error, Loading, Success) y CLick en el Card
class ListBreedScreenTest {

    val fakeBreedsList = listOf(
        DogBreedsList(
            "1",
            "Bulldog",
            "https://cdn2.thedogapi.com/images/sGQvQUpsp.jpg"
        ), DogBreedsList(
            "2",
            "Labrador",
            "https://cdn2.thedogapi.com/images/BJa4kxc4X.jpg"
        )
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    /*** State ***/
    @Test
    fun listBreedScreen_verifyThatTheLoadingScreenIsDisplayed() {

        composeTestRule.setContent {
            ListBreedScreen(
                breedsListState = DogUiState.Loading,
                fetchBreedDetails = {},
                reloadFetchBreedList = {},
                navigateToDetailsBreed = {},
            )
        }

        // Buscar el elemento CircularProgressIndicator por su testTag
        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun listBreedScreen_verifyThatTheErrorScreenIsDisplayed() {
        composeTestRule.setContent {
            ListBreedScreen(
                breedsListState = DogUiState.Error,
                fetchBreedDetails = {},
                reloadFetchBreedList = {},
                navigateToDetailsBreed = {},
            )
        }
        // Buscar el elemento Button por su testTag
        composeTestRule.onNodeWithTag("button_reload_fetchBreedList").assertIsDisplayed()
    }

    @Test
    fun listBreedScreen_verifyThatTheSuccessScreenIsDisplayed() {
        composeTestRule.setContent {
            ListBreedScreen(
                breedsListState = DogUiState.Success(breedsList = fakeBreedsList),
                fetchBreedDetails = {},
                reloadFetchBreedList = {},
                navigateToDetailsBreed = {},
            )
        }

        // Test para dos elementos DogBreedsList
        composeTestRule.onNodeWithText("Bulldog").assertIsDisplayed()
        composeTestRule.onNodeWithText("Labrador").assertIsDisplayed()

    }

    /*** Click ***/

    // Comprueba el click sobre un Card
    @Test
    fun listBreedScreen_onCardClick_callsNavigationAndFetchDetails() {

        var navigateCalled = false
        var fakeReferenceImageID: String? = "https://cdn2.thedogapi.com/images/sGQvQUpsp.jpg"

        composeTestRule.setContent {
            ListBreedScreen(
                breedsListState = DogUiState.Success(breedsList = fakeBreedsList),
                fetchBreedDetails = { fakeReferenceImageID = it },
                reloadFetchBreedList = {},
                navigateToDetailsBreed = { navigateCalled = true },
            )
        }

        // Simula el clic sobre el texto "Bulldog", que representa la Card
        composeTestRule.onNodeWithText("Bulldog").performClick()

        assert(navigateCalled)
        assert((fakeReferenceImageID == "https://cdn2.thedogapi.com/images/sGQvQUpsp.jpg"))
    }

    // Comprueba click en Button "reload"
    @Test
    fun listBreedScreen_onButtonClick_callsReloadFetchBreedList() {

        var reloadFetchBreedListCalled = false

        composeTestRule.setContent {
            ListBreedScreen(
                breedsListState = DogUiState.Error,
                fetchBreedDetails = {},
                reloadFetchBreedList = { reloadFetchBreedListCalled = true },
                navigateToDetailsBreed = {},
            )
        }

        //Simula el click en el Button
        composeTestRule.onNodeWithTag("button_reload_fetchBreedList").performClick()
        assert(reloadFetchBreedListCalled)

    }

}