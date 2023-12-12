package com.weather.app.presentation.search_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.weather.app.domain.model.City
import com.weather.app.presentation.components.CityItem
import com.weather.app.testtags.TestTags
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchScreenViewModel = hiltViewModel(),
    q: String?
) {

    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    var query by remember { mutableStateOf(q ?: "") }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SearchScreenViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = query) {
        snapshotFlow { query }
            .debounce(300)
            .filter { it.length >= 3 }
            .distinctUntilChanged()
            .map {
                viewModel.onEvent(SearchScreenEvent.CitiesSearchResults(it))
            }
            .flowOn(Dispatchers.Default)
            .collect()
    }

    Scaffold(
        topBar = {
            Icon(Icons.Default.ArrowBack, "", modifier = Modifier
                .padding(10.dp)
                .clickable {
                    navController.popBackStack()
                }
                .testTag(TestTags.IconBack)
            )
        },
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier.testTag(TestTags.SnackBar)
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                OutlinedTextField(
                    shape = RoundedCornerShape(200.dp),
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = ""
                        )
                    },
                    value = query,
                    onValueChange = { value ->
                        query = value
                    },
                    placeholder = { Text("Search for cities") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .testTag(TestTags.SearchField),
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable {
                                        query = ""
                                    }
                                    .testTag(TestTags.ClearIcon)
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (state.data.isNotEmpty() && query.isNotEmpty()) {
                    LazyColumn {
                        items(state.data) { cityData ->
                            CityItem(city = cityData) { city ->
                                viewModel.onEvent(SearchScreenEvent.CitySelection(city))
                                query = ""
                            }
                        }
                    }
                }
                if (query.isEmpty()) {
                    RecentChips(state, viewModel)
                }
            }
        }

    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
private fun RecentChips(
    state: SearchScreenState,
    viewModel: SearchScreenViewModel
) {
    if (state.selectedCities.isNotEmpty())
        Text(
            "Recent locations:",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag(TestTags.RecentLocationsText)
        )
    FlowRow(
        modifier = Modifier.padding(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        state.selectedCities.forEach { city ->
            FilterChip(modifier = Modifier.padding(end = 5.dp),
                trailingIcon = {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "", modifier = Modifier.clickable {
                            viewModel.onEvent(SearchScreenEvent.DeleteCity(city))
                        }
                    )
                },
                shape = RoundedCornerShape(200.dp),
                selected = city.id == viewModel.currentLocationId.value,
                onClick = {
                    viewModel.onEvent(SearchScreenEvent.CitySelection(city))
                },
                label = {
                    Text(text = city.localizedName)
                })
        }
    }
}

@Preview
@Composable
fun RecentChipsPreview() {
    val recentCities = listOf(
        City("1", "Cairo", "Egypt", "Cairo"),
        City("2", "Alex", "Egypt", "Alex")
    )
    val viewModel: SearchScreenViewModel = hiltViewModel()
    RecentChips(state = SearchScreenState(selectedCities = recentCities), viewModel)
}

