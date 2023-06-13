package com.nganga.robert.chargelink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R

@Composable
fun SelectVehicleScreen(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SelectVehicleAppBar(
            title = stringResource(id = R.string.select_vehicle),
            onAddClicked = {}
        )
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