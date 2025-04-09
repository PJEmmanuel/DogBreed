package com.example.dogbreed.ui.screens.welcome

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.dogbreed.R
import com.example.dogbreed.ui.screens.DogViewModel

@Composable
fun WelcomeScreen(
    fetchBreedList: () -> Unit,
    navigateToListBreed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable {
                fetchBreedList()
                navigateToListBreed()
            }, contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.Tap_to_get_the_breeds))
    }
}