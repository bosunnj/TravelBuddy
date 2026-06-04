package com.jenstine.travelKing.ui.screens.see

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.jenstine.travelKing.domain.model.TravelDestination
import com.jenstine.travelKing.ui.components.DestinationCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeeScreen(viewModel: SeeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val viewMode by viewModel.viewMode.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            SegmentedButton(
                selected = viewMode == SeeViewMode.GALLERY,
                onClick = { viewModel.setViewMode(SeeViewMode.GALLERY) },
                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
            ) { Text("Gallery") }
            SegmentedButton(
                selected = viewMode == SeeViewMode.MAP,
                onClick = { viewModel.setViewMode(SeeViewMode.MAP) },
                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
            ) { Text("Map") }
        }

        when (val state = uiState) {
            is SeeUiState.Loading -> LoadingContent()
            is SeeUiState.Success -> when (viewMode) {
                SeeViewMode.GALLERY -> GalleryContent(state.destinations)
                SeeViewMode.MAP     -> MapContent(state.destinations)
            }
            is SeeUiState.Error -> ErrorContent(
                message = state.message,
                onRetry = viewModel::retry
            )
        }
    }
}

@Composable
private fun GalleryContent(destinations: List<TravelDestination>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(destinations, key = { it.id }) { destination ->
            DestinationCard(destination = destination)
        }
    }
}

@Composable
private fun MapContent(destinations: List<TravelDestination>) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(20.0, 0.0), 2f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        destinations.forEach { destination ->
            Marker(
                state = rememberMarkerState(
                    position = LatLng(destination.latitude, destination.longitude)
                ),
                title = destination.name,
                snippet = destination.country
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(message: String, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onRetry) { Text("Retry") }
        }
    }
}
