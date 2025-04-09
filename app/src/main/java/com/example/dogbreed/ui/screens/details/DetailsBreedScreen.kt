package com.example.dogbreed.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dogbreed.DogBreedAppBar
import com.example.dogbreed.R
import com.example.dogbreed.domain.model.DogBreedDetails
import com.example.dogbreed.ui.screens.DogUiState
import com.example.dogbreed.ui.screens.DogViewModel
import com.example.dogbreed.ui.screens.state.ErrorScreen
import com.example.dogbreed.ui.screens.state.LoadingScreen

@Composable
fun DetailsBreedScreen(
    breedDetailsState: DogUiState,
    retryToFetchBreedDetails: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    when (breedDetailsState) {
        is DogUiState.Loading -> LoadingScreen()
        is DogUiState.Success -> DetailsBreed(
            breedDetails = breedDetailsState.breedDetails,
            navigateBack = navigateBack,
            modifier = modifier
        )

        is DogUiState.Error -> ErrorScreen(
            callToReload = retryToFetchBreedDetails,
            modifier = modifier.testTag("button_reload_retryToFetchBreedDetails")
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsBreed(
    navigateBack: () -> Unit,
    breedDetails: DogBreedDetails?,
    modifier: Modifier
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        contentWindowInsets = WindowInsets.safeGestures,
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DogBreedAppBar(
                currentScreen = "${breedDetails?.name}",
                scrollBehavior = scrollBehavior,
                popBackArrow = navigateBack,
                onNavigateBack = true
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .clip(ShapeDefaults.Medium)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                ,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageBreedSelected(breedDetails?.url, Modifier)
                Spacer(Modifier.padding(16.dp))
                TextBreedDetails(R.string.name_breed, breedDetails?.name, modifier = Modifier.testTag("dog_name"))
                TextBreedDetails(R.string.bred_for, breedDetails?.bredFor, modifier = Modifier.testTag("bred_for"))
                TextBreedDetails(R.string.Breed_group, breedDetails?.breedGroup, modifier = Modifier.testTag("breed_group"))
                TextBreedDetails(R.string.Life_span, breedDetails?.lifeSpan, modifier = Modifier.testTag("life_span"))
                TextBreedDetails(R.string.temperament, breedDetails?.temperament, modifier = Modifier.testTag("dog_temperament"))
                TextBreedDetails(R.string.details_origin, breedDetails?.origin, modifier = Modifier.testTag("dog_origin"))
            }
        }
    }
}

@Composable
fun TextBreedDetails(
    title: Int,
    detail: String?,
    modifier: Modifier
) {
    if (detail != "") Row(modifier) {
        Text(
            text = stringResource(title),
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = detail ?: "",
        )
    }
}

@Composable
fun ImageBreedSelected(imageReference: String?, modifier: Modifier) {

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageReference)
            .crossfade(true)
            .build(),
        error = painterResource(R.drawable.ic_broken_image),
        contentDescription = "Image",
        modifier = modifier.clip(shape = ShapeDefaults.Medium)
    )
}