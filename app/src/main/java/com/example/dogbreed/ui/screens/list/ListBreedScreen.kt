package com.example.dogbreed.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dogbreed.DogBreedAppBar
import com.example.dogbreed.R
import com.example.dogbreed.domain.model.DogBreedsList
import com.example.dogbreed.ui.screens.DogUiState
import com.example.dogbreed.ui.screens.state.ErrorScreen
import com.example.dogbreed.ui.screens.state.LoadingScreen

@Composable
fun ListBreedScreen(
    navigateToDetailsBreed: () -> Unit,
    fetchBreedDetails: (String) -> Unit,
    reloadFetchBreedList: () -> Unit,
    breedsListState: DogUiState,
    modifier: Modifier = Modifier
) {

    when (breedsListState) {
        is DogUiState.Loading -> LoadingScreen()
        is DogUiState.Success -> ListBreed(
            navigateToDetailsBreed = navigateToDetailsBreed,
            fetchBreedDetails = fetchBreedDetails,
            breedList = breedsListState.breedsList,
            modifier = modifier
        )

        is DogUiState.Error -> ErrorScreen(
            callToReload = reloadFetchBreedList,
            modifier = modifier.testTag("button_reload_fetchBreedList")
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListBreed(
    navigateToDetailsBreed: () -> Unit,
    fetchBreedDetails: (String) -> Unit,
    breedList: List<DogBreedsList>,
    modifier: Modifier
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        contentWindowInsets = WindowInsets.safeGestures,
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DogBreedAppBar(
                currentScreen = stringResource(R.string.app_name),
                scrollBehavior = scrollBehavior,
                popBackArrow = {},
                onNavigateBack = false,
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                contentPadding = innerPadding,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)

            ) {
                items(items = breedList, key = { it.id }) { dogBreed ->
                    CardBreed(
                        navigateToDetailsBreed = navigateToDetailsBreed,
                        fetchBreedDetails = fetchBreedDetails,
                        breed = dogBreed,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun CardBreed(
    navigateToDetailsBreed: () -> Unit,
    fetchBreedDetails: (String) -> Unit,
    breed: DogBreedsList,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .clickable {
                navigateToDetailsBreed()
                fetchBreedDetails(breed.referenceImageId ?: "")
            },
        shape = ShapeDefaults.Small,
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Text(
            text = breed.name,
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp, start = 8.dp, end = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}