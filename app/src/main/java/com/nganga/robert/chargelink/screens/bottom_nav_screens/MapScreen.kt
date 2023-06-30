package com.nganga.robert.chargelink.screens.bottom_nav_screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.nganga.robert.chargelink.models.NewChargingStation
import com.nganga.robert.chargelink.ui.components.ChargingStationItem
import com.nganga.robert.chargelink.ui.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun MapScreen(
    viewModel: HomeScreenViewModel
){

    val state = viewModel.homeScreenState
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-1.286389, 36.817223), 10f)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = MapProperties(
                isMyLocationEnabled = true,
                mapType = MapType.NORMAL
            ),
            cameraPositionState = cameraPositionState,
            contentPadding = PaddingValues(
                bottom = screenHeight * 0.42f,
            ),

        ){
//            val icon = Icons.Default.Charger
//            val marker = BitmapDescriptorFactory.fromBitmap(
//                icon.toBitmap(iconSize, iconColor, density)
//            )

            state.nearbyStations.forEachIndexed{ index, station ->
                Marker(
                    state = MarkerState(
                        position = LatLng(station.lat.toDouble(), station.long.toDouble()),
                    ),
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN),
                    title = station.name,
                    onClick = {
                        scope.launch {
                            listState.animateScrollToItem(index)
                        }
                        true
                    }

                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
        ) {
            ChargingStationsSection(
                stations = state.nearbyStations,
                width = screenWidth,
                listState = listState
            )
        }
    }
}

@Composable
fun ChargingStationsSection(
    stations: List<NewChargingStation>,
    modifier: Modifier = Modifier,
    listState: LazyListState,
    width: Dp
){
    LazyRow(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(horizontal = 10.dp)
    ){
        items(stations){ station ->
            ChargingStationItem(
                station = station,
                modifier = Modifier.width(width)
            )
        }
    }
}

