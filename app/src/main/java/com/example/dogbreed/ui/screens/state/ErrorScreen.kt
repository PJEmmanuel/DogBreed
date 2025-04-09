package com.example.dogbreed.ui.screens.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dogbreed.R

@Composable
fun ErrorScreen(
    callToReload : () -> Unit,
    modifier: Modifier
) {
    Box(modifier.fillMaxSize(), Alignment.Center) {
        Button(
            onClick = callToReload,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 16.dp),
            modifier = Modifier
        ) {
            Text(text = stringResource(R.string.button_reload))
        }
    }
}