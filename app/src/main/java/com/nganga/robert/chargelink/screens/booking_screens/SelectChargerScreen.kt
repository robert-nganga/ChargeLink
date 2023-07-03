package com.nganga.robert.chargelink.screens.booking_screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.Charger

@Composable
fun SelectChargerScreen(
    bookingViewModel: BookingViewModel,
    onBackButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    stationId: String?,
    //chargerId: String?
){
    var selectedId by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true){
        stationId?.let {
            bookingViewModel.getStation(it)
        }
    }

    val chargers = bookingViewModel.station.chargers

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            SelectChargerScreenTopAppBar(
                title = stringResource(id = R.string.select_charger),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            ChargerList(
                modifier = Modifier
                    .fillMaxHeight(),
                chargers = chargers,
                onChargerSelected ={ chargerId ->
                    selectedId = chargerId
                },
                selectedId = selectedId,
            )
            Spacer(modifier = Modifier.height(70.dp))
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = {
                    onBackButtonClicked.invoke()
                },
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.back)
                )
            }

            Button(
                onClick = {
                    onNextButtonClicked.invoke()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.continues)
                )
            }

        }


    }


}

@Composable
fun SelectChargerScreenTopAppBar(
    modifier: Modifier = Modifier,
    title: String
){
    Row(
        modifier = modifier.padding(top = 10.dp, end = 10.dp),
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

@Composable
fun ChargerList(
    chargers: List<Charger>,
    modifier: Modifier = Modifier,
    onChargerSelected: (String)->Unit,
    selectedId: String
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
    ){
        items(chargers){ charger ->
            ChargerListItem(
                charger = charger,
                modifier = Modifier.fillParentMaxWidth(),
                selected = selectedId == charger.id,
                onChargerSelected = {
                    onChargerSelected.invoke(charger.id)
                }
            )
        }
    }

}

@Composable
fun ChargerListItem(
    charger: Charger,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onChargerSelected: ()->Unit
){

    Card(
        modifier = modifier.padding(bottom = 10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ChargerTextColumn(
                modifier = Modifier.weight(1f),
                headerText = charger.plug,
                trailingText = "",
                icon = painterResource(id = R.drawable.ic_ev_plug_j1772_t1),
                iconTint = MaterialTheme.colorScheme.primary
            )
            ChargerTextColumn(
                modifier = Modifier.weight(1f),
                headerText = stringResource(id = R.string.max_power),
                trailingText = charger.power,
            )
            RadioButton(
                modifier = Modifier.weight(1f),
                selected = selected,
                onClick = {
                    onChargerSelected.invoke()
                }
            )
        }
    }

}

@Composable
fun ChargerTextColumn(
    headerText: String,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    iconTint: Color = MaterialTheme.colorScheme.onSurface,
    trailingText: String,
){
    Column(
        modifier = modifier.padding(5.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = headerText,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.outline
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        if (icon != null){
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = iconTint
            )
        }else{
            Text(
                text = trailingText,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}