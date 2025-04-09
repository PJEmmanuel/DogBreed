package com.example.dogbreed

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dogbreed.navigation.NavigationWrapper
import com.example.dogbreed.ui.screens.DogViewModel
import com.example.dogbreed.ui.utils.ArrowBackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogBreedAppBar(
    popBackArrow: () -> Unit,
    onNavigateBack: Boolean,
    currentScreen: String,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        modifier = modifier.padding(bottom = 16.dp),
        title = { Text(currentScreen) },
        navigationIcon = {
            if (onNavigateBack) ArrowBackButton(popBackArrow)
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun DogBreedApp(

) {
    val dogBreedViewModel: DogViewModel = viewModel(factory = DogViewModel.factory)

    // UiState
    val breedsListState by dogBreedViewModel.breedsListState.collectAsState()
    val breedDetailsState by dogBreedViewModel.breedDetailsState.collectAsState()

    NavigationWrapper(
        fetchBreedList = dogBreedViewModel::fetchBreedList,
        breedsListState = breedsListState,
        fetchBreedDetails = dogBreedViewModel::fetchBreedDetails,
        reloadFetchBreedList = dogBreedViewModel::fetchBreedList,
        breedDetailsState = breedDetailsState,
        retryToFetchBreedDetails = dogBreedViewModel::retryToFetchBreedDetails,
    )
}


