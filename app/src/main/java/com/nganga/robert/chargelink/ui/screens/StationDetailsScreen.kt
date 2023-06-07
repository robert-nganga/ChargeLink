package com.nganga.robert.chargelink.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.NearMe
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.sp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.Amenities
import com.nganga.robert.chargelink.models.Charger
import com.nganga.robert.chargelink.models.OpenDay
import com.nganga.robert.chargelink.models.Review
import com.nganga.robert.chargelink.ui.components.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StationDetailsScreen(){
    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }
    var rating by rememberSaveable {
        mutableStateOf(3)
    }
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = bottomState,
        sheetBackgroundColor = MaterialTheme.colorScheme.background,
        sheetContentColor = MaterialTheme.colorScheme.onBackground,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetContent = {
            ReviewBottomSheetContent(
                onRatingChanged = { _, _ ->
                    scope.launch { bottomState.hide() }
                },
                rating = rating,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.TopCenter
        ) {
            ImageHeaderSection(
                image = painterResource(id = R.drawable.station3),
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
                    rating = 4
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
                    2 -> ReviewSection(
                        totalReviews = 120,
                        averageRating = 4.0f,
                        reviews = listOf(
                            Review(
                                userName = "John Doe",
                                userImage = painterResource(id = R.drawable.user1),
                                date = "2023-06-04",
                                time = "11:02 AM",
                                message = "This was a great product! I would definitely recommend it to others.",
                                rating = 5
                            ),
                            Review(
                                userName = "Jane Doe",
                                userImage = painterResource(id = R.drawable.user5),
                                date = "2023-06-03",
                                time = "10:00 AM",
                                message = "This product was not as good as I expected. I would not recommend it to others.",
                                rating = 2
                            ),
                            Review(
                                userName = "Bill Smith",
                                userImage = painterResource(id = R.drawable.user2),
                                date = "2023-06-02",
                                time = "9:00 AM",
                                message = "This product was okay. I wouldn't say it was great, but it wasn't bad either.",
                                rating = 3
                            ),
                            Review(
                                userName = "Susan Jones",
                                userImage = painterResource(id = R.drawable.user4),
                                date = "2023-06-01",
                                time = "8:00 AM",
                                message = "I loved this product! It was everything I was looking for and more.",
                                rating = 4
                            ),
                            Review(
                                userName = "David Brown",
                                userImage = painterResource(id = R.drawable.user3),
                                date = "2023-05-31",
                                time = "7:00 AM",
                                message = "This product was a lifesaver! I don't know what I would have done without it.",
                                rating = 5
                            )
                        ),
                        modifier = Modifier.fillMaxSize(),
                        onRatingChange = {
                            rating = it
                            scope.launch { bottomState.show() }
                            println("ModalBottomSheet visibility ${bottomState.isVisible} and rating:: $rating")
                        }
                    )
                    3 -> ReviewBottomSheetContent(onRatingChanged = { _, _ ->}, rating = 4)
                }
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
    rating: Int
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
                    text = rating.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.width(3.dp))
                Ratings(
                    rating = rating,
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
                    onClick = {  }
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
    var selectedTabIndex by rememberSaveable{
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
            ChargersItem(charger = charger, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp))
        }
    }
}

@Composable
fun ReviewHeaderSection(
    modifier: Modifier = Modifier,
    totalReviews: Int,
    averageRating: Float
){
    Row(modifier = modifier.padding(vertical = 10.dp)) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = averageRating.toString(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 30.sp
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Ratings(
                rating = averageRating.toInt(),
                starSize = 27.dp,
                starColor = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "(${totalReviews}) reviews",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            RatingBar(value = 0.8f, modifier = Modifier.fillMaxWidth(), number = 5)
            Spacer(modifier = Modifier.height(5.dp))
            RatingBar(value = 0.7f, modifier = Modifier.fillMaxWidth(), number = 4)
            Spacer(modifier = Modifier.height(5.dp))
            RatingBar(value = 0.5f, modifier = Modifier.fillMaxWidth(), number = 3)
            Spacer(modifier = Modifier.height(5.dp))
            RatingBar(value = 0.35f, modifier = Modifier.fillMaxWidth(), number = 2)
            Spacer(modifier = Modifier.height(5.dp))
            RatingBar(value = 0.2f, modifier = Modifier.fillMaxWidth(), number = 1)
        }
    }
}

@Composable
fun ReviewSection(
    modifier: Modifier = Modifier,
    totalReviews: Int,
    averageRating: Float,
    reviews: List<Review>,
    onRatingChange: (Int) -> Unit
){
    var selectedSortItem by rememberSaveable {
        mutableStateOf("Most relevant")
    }

    LazyColumn(modifier = modifier, contentPadding = PaddingValues(horizontal = 10.dp)){
        item {
            ReviewHeaderSection(totalReviews = totalReviews, averageRating = averageRating)
        }
        item {
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 0.8.dp)
        }
        item {
            WriteReviewSection(
                currentUserImage = painterResource(id = R.drawable.profile),
                modifier = Modifier.fillMaxWidth(),
                onRatingChange = {onRatingChange(it)}
            )
        }
        item {
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 0.8.dp)
        }
        item {
            SortReviewsSection(
                names = listOf("Most relevant", "Newest", "Highest", "Lowest"),
                selectedItem = selectedSortItem,
                onSelectionChange = { selectedSortItem = it}
            )
        }
        items(reviews){ review ->
            ReviewItem(review = review, modifier = Modifier.fillMaxWidth())
        }
    }

}

@Composable
fun WriteReviewSection(
    currentUserImage: Painter,
    modifier: Modifier = Modifier,
    onRatingChange: (Int)->Unit
){
    Column(
        modifier = modifier.padding(10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = "Write a review",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Share your experience to help others",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = currentUserImage,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .aspectRatio(
                        1f,
                        matchHeightConstraintsFirst = true
                    )
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Ratings(
                rating = 0,
                starSize = 35.dp,
                starColor = MaterialTheme.colorScheme.primary,
                onRatingChanged = {onRatingChange(it)}
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun SortReviewsSection(
    names: List<String>,
    selectedItem: String,
    modifier: Modifier = Modifier,
    onSelectionChange: (String)->Unit
){
    Column(
        modifier = modifier.padding(horizontal = 10.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Sort by",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(5.dp))
        ChipGroup(
            names = names,
            selectedItem = selectedItem,
            onSelectionChange = { onSelectionChange(it) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
