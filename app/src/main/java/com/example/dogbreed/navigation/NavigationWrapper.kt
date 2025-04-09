package com.example.dogbreed.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dogbreed.navigation.screens.DetailsBreed
import com.example.dogbreed.navigation.screens.ListBreed
import com.example.dogbreed.navigation.screens.Welcome
import com.example.dogbreed.ui.screens.DogUiState
import com.example.dogbreed.ui.screens.details.DetailsBreedScreen
import com.example.dogbreed.ui.screens.list.ListBreedScreen
import com.example.dogbreed.ui.screens.welcome.WelcomeScreen

@Composable
fun NavigationWrapper(
    fetchBreedList: () -> Unit,
    breedsListState: DogUiState,
    fetchBreedDetails: (String) -> Unit,
    reloadFetchBreedList: () -> Unit,
    breedDetailsState: DogUiState,
    retryToFetchBreedDetails: () -> Unit,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Welcome) {
        composable<Welcome> {
            WelcomeScreen(
                fetchBreedList = fetchBreedList,
                navigateToListBreed = { navController.navigate(ListBreed) }
            )
        }

        composable<ListBreed> {
            ListBreedScreen(
                navigateToDetailsBreed = { navController.navigate(DetailsBreed) },
                fetchBreedDetails = fetchBreedDetails,
                reloadFetchBreedList = reloadFetchBreedList,
                breedsListState = breedsListState,
            )
        }

        composable<DetailsBreed> {
            DetailsBreedScreen(
                navigateBack = { navController.popBackStack() },
                breedDetailsState = breedDetailsState,
                retryToFetchBreedDetails = retryToFetchBreedDetails,
            )
        }
    }
}