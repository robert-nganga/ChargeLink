package com.nganga.robert.chargelink.screens.bottom_nav_screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.NearMe
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.Charger
import com.nganga.robert.chargelink.models.Review
import com.nganga.robert.chargelink.ui.components.*
import com.nganga.robert.chargelink.ui.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StationDetailsScreen(
    id: String?,
    viewModel: HomeScreenViewModel,
    onBookClicked: (chargerId: String?, stationId: String)-> Unit,
){
    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }
    var rating = 0

    LaunchedEffect(key1 = true){
        id?.let {
            viewModel.getStationById(it)
        }
    }
    val chargingStation =  viewModel.stationDetailsScreenState.chargingStation
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = bottomState,
        sheetBackgroundColor = MaterialTheme.colorScheme.background,
        sheetContentColor = MaterialTheme.colorScheme.onBackground,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetContent = {
            ReviewBottomSheetContent(
                onRatingChanged = { rating, message ->
                    scope.launch { bottomState.hide() }
                    Log.i("StationDetailsScreen", "Rating: $rating, Message: $message, id ${chargingStation.id}")
                    viewModel.submitReview(chargingStation.id, rating, message)
                    // Refresh the station details screen to reflect the new review
                    viewModel.getStationById(chargingStation.id)
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
                    name = chargingStation.name,
                    location = chargingStation.location,
                    rating = 4,
                    onBookClicked = { onBookClicked(null, chargingStation.id) }
                )
                Spacer(modifier = Modifier.height(10.dp))
                TabView(
                    tabTitles = listOf("Overview", "Chargers", "Reviews", "Photos"),
                    onTabSelected = {selectedTabIndex = it},
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                )
                when (selectedTabIndex){
                    0 -> OverviewSection(
                        description = chargingStation.description,
                        phone = chargingStation.phone,
                        openHours = chargingStation.openHours,
                        openDays = chargingStation.openDays,
                        amenities = chargingStation.amenities
                    )
                    1 -> ChargerSection(
                        chargers = chargingStation.chargers,
                        modifier = Modifier.fillMaxSize(),
                        onBookChargerClicked = { chargerId ->
                            onBookClicked(chargerId, chargingStation.id)
                        }
                    )
                    2 -> ReviewSection(
                        totalReviews = 120,
                        averageRating = 4.0f,
                        reviews = chargingStation.reviews,
                        modifier = Modifier.fillMaxSize(),
                        onRatingChange = {
                            Log.i("StationDetailsScreen", "Rating: $it")
                            rating = it
                            scope.launch { bottomState.show() }
                        }
                    )
                    3 -> { }
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
fun DescriptionSection(
    modifier: Modifier = Modifier,
    name: String,
    location: String,
    rating: Int,
    onBookClicked: () -> Unit
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
                    onClick = { onBookClicked.invoke() }
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
fun ChargerSection(
    modifier: Modifier = Modifier,
    chargers: List<Charger>,
    onBookChargerClicked: (String)->Unit
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
    ){
        items(chargers){ charger->
            ChargersItem(
                charger = charger,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                onBookChargerClicked = { onBookChargerClicked.invoke(charger.id) }
            )
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
