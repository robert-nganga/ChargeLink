package com.nganga.robert.chargelink.screens.bottom_nav_screens.map_screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.ChargingStation
import com.nganga.robert.chargelink.models.PlaceSuggestion
import com.nganga.robert.chargelink.ui.components.ChargingStationItem
import com.nganga.robert.chargelink.ui.components.HorizontalDivider
import com.nganga.robert.chargelink.ui.components.ProgressDialog
import com.nganga.robert.chargelink.ui.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    homeScreenViewModel: HomeScreenViewModel,
    mapScreenViewModel: MapScreenViewModel,
    onStationClicked: (String) -> Unit,
){

    val homeScreenState = homeScreenViewModel.homeScreenState
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    //Initial camera state is Nairobi
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(homeScreenState.currentLocation, 10f)
    }

    val placeSuggestionsState = mapScreenViewModel.placeSuggestionsState
    val nearbyStationsState = mapScreenViewModel.nearbyStationsState
    var chargingStations by remember{
        mutableStateOf(listOf<ChargingStation>())
    }

    LaunchedEffect(key1 = nearbyStationsState.location){
        chargingStations = if (nearbyStationsState.location != null) nearbyStationsState.nearbyStations
        else homeScreenState.nearbyStations

        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(
                nearbyStationsState.location ?: homeScreenState.currentLocation,
                20f
            )
        )
    }




    var isSearchViewFocused by remember{
        mutableStateOf(false)
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (nearbyStationsState.isLoading){
            ProgressDialog(text = stringResource(id = R.string.please_wait))
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = MapProperties(
                isMyLocationEnabled = true,
                mapType = MapType.NORMAL
            ),
            cameraPositionState = cameraPositionState,
            contentPadding = PaddingValues(
                bottom = screenHeight * 0.48f,
                top = 70.dp,
            ),
            onMyLocationButtonClick = {
                mapScreenViewModel.clearLocation()
                true
            }
            ) {
            chargingStations.forEachIndexed { index, station ->
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
                stations = chargingStations,
                width = screenWidth - 20.dp,
                listState = listState,
                onStationClicked = { stationId ->
                    onStationClicked(stationId)
                }
            )
        }
        if (isSearchViewFocused) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = 70.dp, start = 10.dp, end = 10.dp, bottom = 65.dp)

            ) {
                PlaceSuggestionsSection(
                    suggestions = placeSuggestionsState.suggestions,
                    onItemClick = { id->
                        isSearchViewFocused = false
                        mapScreenViewModel.onQueryChange("")
                        focusManager.clearFocus()
                        mapScreenViewModel.clearSuggestions()
                        mapScreenViewModel.getCoordinatesFromPlaceId(id)
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                isSearchViewFocused = true
                            }
                        },
                    value = placeSuggestionsState.query,
                    onValueChange = mapScreenViewModel::onQueryChange,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(
                            alpha = 1.0f
                        ),
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    label = {
                        if (!isSearchViewFocused){
                            Text(
                                text = stringResource(id = R.string.search_for_a_place),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    },
                    trailingIcon = {
                        if (placeSuggestionsState.query.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    mapScreenViewModel.onQueryChange("")
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    },
                    leadingIcon = {
                        if (isSearchViewFocused) {
                            IconButton(
                                onClick = {
                                    isSearchViewFocused = false
                                    mapScreenViewModel.onQueryChange("")
                                    focusManager.clearFocus()
                                    mapScreenViewModel.clearSuggestions()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Close",
                                    modifier = Modifier
                                        .padding(end= 5.dp),

                                )
                            }
                        }else {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "location icon"
                            )
                        }
                    },
                    shape = RoundedCornerShape(30.dp),
                )
            }
        }
    }
}

@Composable
fun ChargingStationsSection(
    stations: List<ChargingStation>,
    modifier: Modifier = Modifier,
    listState: LazyListState,
    width: Dp,
    onStationClicked: (String)->Unit
){
    LazyRow(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(horizontal = 10.dp)
    ){
        items(stations){ station ->
            ChargingStationItem(
                station = station,
                modifier = Modifier.width(width),
                onStationClicked = {
                    onStationClicked(station.id)
                }
            )
        }
    }
}

@Composable
fun PlaceSuggestionsSection(
    modifier: Modifier = Modifier,
    suggestions: List<PlaceSuggestion>,
    onItemClick: (String)->Unit
){
    LazyColumn(modifier = modifier){
        items(suggestions){ suggestion ->
            SuggestionItem(
                suggestion = suggestion,
                onItemClick = { id ->
                    onItemClick(id)
                }
            )
        }
    }
}

@Composable
fun SuggestionItem(
    suggestion: PlaceSuggestion,
    modifier: Modifier = Modifier,
    onItemClick: (String)->Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
            .clickable {
                onItemClick(suggestion.placeId)
            },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = "location icon",
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = suggestion.primaryText,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = suggestion.secondaryText,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider()
        }
    }
}

fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

    vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

