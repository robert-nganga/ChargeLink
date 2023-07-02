package com.nganga.robert.chargelink.screens.booking_screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.Charger
import com.nganga.robert.chargelink.ui.components.TextColumn

@Composable
fun SelectChargerScreen(
){
    var selectedIndex by remember { mutableStateOf(-1) }

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
            chargers = emptyList(),
            onChargerSelected ={ index, charger ->
                selectedIndex = index
           },
            selectedIndex = selectedIndex,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = {  },
                shape = RoundedCornerShape(20.dp),
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
                onClick = {  },
                shape = RoundedCornerShape(20.dp),
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
        modifier = modifier.padding(vertical = 10.dp),
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
    onChargerSelected: (Int, Charger)->Unit,
    selectedIndex: Int
){
    LazyColumn(
        modifier = modifier
    ){
        items(chargers.size){ index ->
            ChargerListItem(
                charger = chargers[index],
                modifier = Modifier.fillParentMaxWidth(),
                selected = index == selectedIndex,
                onChargerSelected = {
                    onChargerSelected(index, chargers[index])
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
    onChargerSelected: (Charger)->Unit
){

    Card(
        modifier = modifier.padding(horizontal = 10.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ){
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextColumn(
                headerText = charger.plug,
                trailingText = "",
                icon = painterResource(id = charger.image),
            )
            TextColumn(
                headerText = stringResource(id = R.string.max_power),
                trailingText = charger.power,
            )
            RadioButton(
                selected = selected,
                onClick = {
                    onChargerSelected(charger)
                }
            )
        }

    }

}