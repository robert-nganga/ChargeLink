package com.nganga.robert.chargelink.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.NearMe
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.Amenities
import com.nganga.robert.chargelink.models.Charger
import com.nganga.robert.chargelink.models.OpenDay
import com.nganga.robert.chargelink.ui.components.ChargersItem
import com.nganga.robert.chargelink.ui.components.IconText
import com.nganga.robert.chargelink.ui.components.OverviewSection
import com.nganga.robert.chargelink.ui.components.Ratings

@Composable
fun StationDetailsScreen(){
    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        ImageHeaderSection(
            image = painterResource(id = R.drawable.station1),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.25f))
            DescriptionSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(MaterialTheme.colorScheme.background),
                name = "EvGo Charger",
                location = "Waiyaki way, Westlands",
                rating = "4.8"
            )
            Spacer(modifier = Modifier.height(10.dp))
            TabView(
                tabTitles = listOf("Overview", "Chargers", "Reviews", "Photos"),
                onTabSelected = {selectedTabIndex = it},
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
            )
            when (selectedTabIndex){
                0 -> OverviewSection(
                    description = "We offer charging services for various types of vehicles and manufactures, our chargers are fas.",
                    phone = "012345345",
                    openHours = "12 Hours",
                    openDays = listOf(
                        OpenDay(day = "Monday", hours = "08:00 AM - 10:00 PM"),
                        OpenDay(day = "Tuesday", hours = "08:00 AM - 10:00 PM"),
                        OpenDay(day = "Wednesday", hours = "08:00 AM - 10:00 PM"),
                        OpenDay(day = "Thursday", hours = "08:00 AM - 10:00 PM"),
                        OpenDay(day = "Friday", hours = "08:00 AM - 10:00 PM"),
                        OpenDay(day = "Saturday", hours = "10:00 AM - 6:00 PM"),
                    ),
                    amenities = Amenities(
                        wifi = true,
                        restaurants = true,
                        restrooms = false,
                        tyrePressure = true,
                        loungeArea = false,
                        maintenance = true,
                        shops = false
                    )
                )
                1 -> ChargerSection(chargers = listOf(
                    Charger(plug = "CCS 1 DC", power = "360kW", image = painterResource(
                        id = R.drawable.ic_ev_plug_ccs2), isAvailable = true
                    ),
                    Charger(plug = "CCS 2 DC", power = "360kW", image = painterResource(
                        id = R.drawable.ic_ev_plug_ccs2_combo), isAvailable = true
                    ),
                    Charger(plug = "Mennekes (Type 2) AC", power = "22kW", image = painterResource(
                        id = R.drawable.ic_ev_plug_iec_mennekes_t2), isAvailable = true
                    ),
                    Charger(plug = "J1772 (Type 1) AC", power = "19.2kW", image = painterResource(
                        id = R.drawable.ic_ev_plug_j1772_t1), isAvailable = true
                    ),
                    Charger(plug = "Tesla NACS AC/DC", power = "250kW", image = painterResource(
                        id = R.drawable.ic_ev_plug_tesla), isAvailable = true
                    )
                ),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun ImageHeaderSection(
    modifier: Modifier = Modifier,
    image: Painter
){
    Box(modifier = modifier) {
        Image(
            painter = image,
            contentDescription = "Station Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BoxIcon(
                icon = Icons.Default.ArrowBack,
                iconSize = 25.dp,
                iconTint = Color.White,
                background = Color(0x4DF2F2F2),
                modifier = Modifier
                    .size(40.dp)
                    .aspectRatio(1f)
            )
            BoxIcon(
                icon = Icons.Outlined.Bookmark,
                iconSize = 25.dp,
                iconTint = Color.White,
                background = Color(0x4DF2F2F2),
                modifier = Modifier
                    .size(40.dp)
                    .aspectRatio(1f)
            )
        }
    }
}

@Composable
fun BoxIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconSize: Dp,
    iconTint: Color,
    background: Color
){
    Box(
        contentAlignment = Alignment.Center, 
        modifier = modifier
            .padding(5.dp)
            .background(background)
            .clip(
                RoundedCornerShape(10.dp)
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(iconSize),
            tint = iconTint
        )
    }
}

@Composable
fun DescriptionSection(
    modifier: Modifier = Modifier,
    name: String,
    location: String,
    rating: String
){
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(bottomStart = 40.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 15.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Open",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White
                )
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 15.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = location,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(3.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = rating,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.width(3.dp))
                Ratings(
                    rating = rating.toFloat(),
                    starSize = 25.dp,
                    starColor = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "(23 reviews)",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){

                IconText(
                    icon = Icons.Outlined.LocationOn,
                    text = "4.2km"
                )
                Spacer(modifier = Modifier.width(5.dp))
                IconText(
                    icon = Icons.Outlined.NearMe,
                    text = "2 min"
                )
                Box(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = "Book"
                    )
                }
            }

        }
    }
}

@Composable
fun TabView(
    modifier: Modifier = Modifier,
    tabTitles: List<String>,
    onTabSelected: (selectedIndex: Int) -> Unit
){
    var selectedTabIndex by remember{
        mutableStateOf(0)
    }

    TabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = MaterialTheme.colorScheme.primary,
        backgroundColor = Color.Transparent,
        modifier = modifier

    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                selectedContentColor = MaterialTheme.colorScheme.primary ,
                unselectedContentColor = MaterialTheme.colorScheme.outline,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(index)
                },
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = if (selectedTabIndex == index) 
                                    MaterialTheme.colorScheme.primary 
                                else 
                                    MaterialTheme.colorScheme.outline
                    )
                )
            }
        }
    }
}

@Composable
fun ChargerSection(
    modifier: Modifier = Modifier,
    chargers: List<Charger>
){
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)){
        items(chargers){ charger->
            ChargersItem(charger = charger, modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp))
        }
    }
}