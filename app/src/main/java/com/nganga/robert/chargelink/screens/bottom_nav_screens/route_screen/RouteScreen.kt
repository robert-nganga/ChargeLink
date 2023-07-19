package com.nganga.robert.chargelink.screens.bottom_nav_screens.route_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.screens.bottom_nav_screens.map_screen.PlaceSuggestionsSection
import com.nganga.robert.chargelink.ui.components.HorizontalDivider
import com.nganga.robert.chargelink.ui.components.ProgressDialog
import com.nganga.robert.chargelink.utils.IconUtils
import com.nganga.robert.chargelink.utils.LocationUtils.toLatLng
import com.nganga.robert.chargelink.utils.LocationUtils.toLatLngBounds


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteScreen(
    routeViewModel: RouteViewModel
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val routeScreenState = routeViewModel.routeScreenState

    val currentLocation = routeViewModel.currentLocation.observeAsState()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation.value?.toLatLng() ?: LatLng(-1.286389,36.817223), 15f)
    }


    LaunchedEffect(key1 = routeScreenState.polyLinePointsState.points){
        if (routeScreenState.polyLinePointsState.points.isNotEmpty()){
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngBounds(
                    routeScreenState.polyLinePointsState.points.toLatLngBounds(),
                    100
                )
            )
        }else {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    currentLocation.value?.toLatLng() ?: LatLng(-1.286389,36.817223),
                    12f
                )
            )
        }

    }

    var isStartQueryFocused by remember { mutableStateOf(false) }
    var isEndQueryFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (routeScreenState.polyLinePointsState.isLoading){
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
                bottom = screenHeight * 0.3f,
                top = 170.dp,
            )
        ){
            Polyline(
                points = routeScreenState.polyLinePointsState.points,
                color = MaterialTheme.colorScheme.surfaceTint,
                width = 8.0f
            )
            Marker(
                state = MarkerState(
                    position = routeScreenState.startLocation,
                ),
                icon = IconUtils.bitmapFromVector(
                    context = LocalContext.current,
                    vectorResId = R.drawable.ic_location_person_filled,
                    color = MaterialTheme.colorScheme.surfaceTint.toArgb()
                ),
                title = stringResource(id = R.string.start)
            )
            Marker(
                state = MarkerState(
                    position = routeScreenState.endLocation,
                ),
                icon = IconUtils.bitmapFromVector(
                    context = LocalContext.current,
                    vectorResId = R.drawable.ic_destination_flag,
                    color = MaterialTheme.colorScheme.surfaceTint.toArgb()
                ),
                title = stringResource(id = R.string.end)
            )
        }


        if (isEndQueryFocused || isStartQueryFocused){
            Box(
                modifier= Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 170.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp)
                ) {
                    HorizontalDivider()
                    PlaceSuggestionsSection(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        suggestions = routeViewModel.placeSuggestions,
                        onItemClick = {name, id->
                            if (isStartQueryFocused){
                                routeViewModel.updateStartLocation(placeName = name, placeId = id)
                            }
                            if (isEndQueryFocused){
                                routeViewModel.updateEndLocation(placeName = name, placeId = id)
                            }
                            isEndQueryFocused = false
                            isStartQueryFocused = false
                            focusManager.clearFocus()
                            routeViewModel.clearSuggestions()
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = if (routeScreenState.isRouteActive) MaterialTheme.colorScheme.background else Color.Transparent,
                )
                .padding(top = 40.dp, end = 10.dp),
            verticalAlignment = Alignment.Top,

        ) {
            if (isStartQueryFocused || isEndQueryFocused || routeScreenState.isRouteActive){
                IconButton(
                    onClick = {
                        focusManager.clearFocus()
                        isStartQueryFocused = false
                        isEndQueryFocused = false
                        routeViewModel.clearRoute()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back"
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextField(
                    modifier = Modifier
                        .height(60.dp)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                isStartQueryFocused = true
                            }
                        },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.your_location)
                        )
                    },
                    value = routeViewModel.startQuery,
                    onValueChange = routeViewModel::onStartQueryChange,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.MyLocation,
                            contentDescription ="my location",
                            tint = if (isStartQueryFocused) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = if (isStartQueryFocused || isEndQueryFocused) Color(0x0D000000) else MaterialTheme.colorScheme.background,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    modifier = Modifier
                        .height(60.dp)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                isEndQueryFocused = true
                            }
                        },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_destination)
                        )
                    },
                    value = routeViewModel.endQuery,
                    onValueChange = routeViewModel::onEndQueryChange,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AddLocation,
                            contentDescription ="my location",
                            tint = if (isEndQueryFocused) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = if (isEndQueryFocused || isStartQueryFocused) Color(0x0D000000) else MaterialTheme.colorScheme.background,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }




    }

}