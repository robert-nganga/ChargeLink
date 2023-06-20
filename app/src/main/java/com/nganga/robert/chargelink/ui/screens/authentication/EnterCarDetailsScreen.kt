package com.nganga.robert.chargelink.ui.screens.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterCarDetailsScreen(
    onFinishClicked: ()->Unit
){
    var manufacturer by remember{
        mutableStateOf("")
    }
    var model by remember{
        mutableStateOf("")
    }
    var batteryCapacity by remember{
        mutableStateOf("")
    }
    var range by remember{
        mutableStateOf("")
    }
    var plug by remember{
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        EnterCarDetailsTopAppBar(
            title = stringResource(id = R.string.enter_car_details),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(id = R.drawable.electric_car),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = manufacturer,
            onValueChange = { manufacturer = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.manufacturer))},
            singleLine = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = model,
            onValueChange = { model = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.model))},
            singleLine = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = batteryCapacity,
                onValueChange = { batteryCapacity = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                label = { Text(text = stringResource(id = R.string.battery_capacity))},
                singleLine = true
            )
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedTextField(
                value = plug,
                onValueChange = { plug = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                label = { Text(text = stringResource(id = R.string.plug))},
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = range,
            onValueChange = { range = it },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.range))},
            singleLine = true
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { onFinishClicked() },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = stringResource(id = R.string.finish))
        }

    }
}

@Composable
fun EnterCarDetailsTopAppBar(
    modifier: Modifier = Modifier,
    title: String
){
    Row(
        modifier = modifier.padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            onClick = {  }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}