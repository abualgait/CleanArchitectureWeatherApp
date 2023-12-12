package com.weather.app.presentation.settings_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weather.app.BuildConfig
import com.weather.app.R

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painterResource(R.drawable.app_icon),
            contentDescription = ""
        )


        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = stringResource(R.string.app_name),
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "v${BuildConfig.VERSION_NAME} #${BuildConfig.VERSION_CODE}",
            color = MaterialTheme.colorScheme.onBackground
        )

    }
}