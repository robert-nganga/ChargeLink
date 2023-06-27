package com.nganga.robert.chargelink.screens.bottom_nav_screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.location.LocationServices
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.ChargingStation
import com.nganga.robert.chargelink.ui.components.GarageItem
import com.nganga.robert.chargelink.ui.components.NearbyListItem
import com.nganga.robert.chargelink.ui.components.PermissionDialog
import com.nganga.robert.chargelink.ui.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreen(
    activity: Activity,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel,
    onNearByItemClick: (String) -> Unit
){


    val context = LocalContext.current
    var shouldShowPermissionDialog by remember {
        mutableStateOf(false)
    }
    val locationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted->
            if (isGranted) {
                locationClient.lastLocation
                    .addOnSuccessListener { location ->
                    if (location != null) {

                    }
                }
            }
        }
    )




    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner
    ) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {

                when {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED -> {

                    }
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        context as Activity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) -> {
                        shouldShowPermissionDialog = true
                    }
                    else -> {
                        locationPermissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (shouldShowPermissionDialog) {
        PermissionDialog(
            text = stringResource(id = R.string.location_reason),
            onDismiss = {
                shouldShowPermissionDialog = false
            },
            onOkayClick = {
                shouldShowPermissionDialog = false
                locationPermissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        )
    }
    val state by viewModel.state
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        TopAppBar(
            name = state.currentUser.name,
            location = state.currentUser.location,
            profile = painterResource(id = state.currentUser.image),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(15.dp))
        SearchSection()
        Spacer(modifier = Modifier.height(10.dp))
        GarageItem(
            model = state.currentUser.cars[0].model,
            manufacturer = state.currentUser.cars[0].manufacturer,
            capacity = state.currentUser.cars[0].batteryCapacity,
            range = state.currentUser.cars[0].range,
            connectors = listOf("AC", "DC"),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "NearBy Stations",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        NearbySection(
            stations = state.nearbyStations,
            onNearByItemClick = { onNearByItemClick(it) }
        )


    }
}

@Composable
fun TopAppBar(
    name: String,
    location: String,
    profile: Painter,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(vertical = 10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "location",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.outline
                    )
                )
            }
        }

        RoundImage(
            image = profile,
            modifier = Modifier.size(70.dp)
        )
    }
}

@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
){
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
            .padding(2.dp)
            .clip(CircleShape)
    )
}

@Composable
fun SearchSection(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(5.dp)
                .weight(8.5f)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Row(
                modifier = modifier.padding(10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search"
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Search")
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .weight(1.5f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onPrimary

            )
        }
    }
}

@Composable
fun NearbySection(
    modifier: Modifier = Modifier,
    stations: List<ChargingStation>,
    onNearByItemClick: (String) ->Unit
){
    LazyColumn(modifier = modifier){
        items(stations){ station->
            NearbyListItem(
                chargingStation = station,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                onNearByItemClick = { onNearByItemClick(it) }
            )
        }
        item {
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}

