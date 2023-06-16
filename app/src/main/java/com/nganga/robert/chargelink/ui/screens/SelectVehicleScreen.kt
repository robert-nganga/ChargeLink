package com.nganga.robert.chargelink.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.Car

@Composable
fun SelectVehicleScreen(){
    var isSelected by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SelectVehicleAppBar(
            title = stringResource(id = R.string.select_vehicle),
            onAddClicked = {}
        )
        SelectVehicleItem(
            vehicle = Car(
                manufacturer = "Range rover",
                imageUrl = R.drawable.ic_carpic,
                model = "Autobiography",
                range = "280km",
                batteryCapacity = "200kW",
                chargingSpeed = "50kW",
                plug = "CCS1 DC"
            ),
            isSelected = isSelected,
            onSelectionChange = {isSelected = it}
        )
        Box(modifier = Modifier.weight(1f))
        Button(
            onClick = {  }
        ) {
            Text(
                text = stringResource(id = R.string.continues)
            )
        }
    }
}

@Composable
fun SelectVehicleAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onAddClicked: ()->Unit
){
    Row(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {  }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        IconButton(
            onClick = {  }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add vehicle"
            )
        }
    }
}

@Composable
fun SelectVehicleItem(
    vehicle: Car,
    isSelected: Boolean,
    onSelectionChange: (Boolean)->Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.padding(horizontal = 10.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = vehicle.imageUrl),
                contentDescription = "Vehicle image",
                modifier = Modifier.size(50.dp),
                tint = Color.Blue

            )
            Spacer(modifier = Modifier.width(10.dp))
            Column{
                Text(
                    text = vehicle.manufacturer,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = vehicle.manufacturer,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.outline
                    )
                )
            }
            Box(modifier = Modifier.weight(1f))
            RadioButton(
                selected = isSelected,
                onClick = {
                    onSelectionChange(!isSelected)
                }
            )
        }
    }
}