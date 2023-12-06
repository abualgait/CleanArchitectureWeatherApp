package com.weather.app.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun DisposableEffectWithLifeCycle(
    onStart: () -> Unit,
    onDestroy: () -> Unit,
) {
    // Safely update the current lambdas when a new one is provided
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnDestroy by rememberUpdatedState(onDestroy)

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for lifecycle events
        val observer = LifecycleEventObserver { _, event ->
            when (event) {

                Lifecycle.Event.ON_START -> {
                    currentOnStart()
                }

                Lifecycle.Event.ON_DESTROY -> {
                    currentOnDestroy()
                }

                else -> {}
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

}