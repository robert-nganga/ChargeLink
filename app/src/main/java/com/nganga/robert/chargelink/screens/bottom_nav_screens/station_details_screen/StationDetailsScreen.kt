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
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
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
import com.nganga.robert.chargelink.screens.bottom_nav_screens.station_details_screen.StationDetailsViewModel
import com.nganga.robert.chargelink.ui.components.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StationDetailsScreen(
    id: String?,
    viewModel: StationDetailsViewModel,
    onBookClicked: (stationId: String)-> Unit,
){
    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }

    var message by remember{
        mutableStateOf("")
    }

    var rating by remember{
        mutableStateOf(0)
    }

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
                onRatingChanged = {
                    rating = it
                },
                rating = rating,
                modifier = Modifier.fillMaxWidth(),
                onMessageChange = {
                    message = it
                },
                onSubmitClicked = {
                    scope.launch { bottomState.hide() }
                    viewModel.submitReview(chargingStation.id, rating, message)

                    // Refresh the station details screen to reflect the new review
                    viewModel.getStationById(chargingStation.id)
                },
                message = message
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
                    distance = chargingStation.distance,
                    location = chargingStation.location,
                    rating = chargingStation.reviews.averageRating(),
                    totalReviews = chargingStation.reviews.size,
                    onBookClicked = { onBookClicked(chargingStation.id) }
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
                        onBookChargerClicked = {
                            onBookClicked(chargingStation.id)
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
fun  ImageHeaderSection(
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
            IconButton(
                onClick = {

                },
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "back",
                    tint = Color.White
                )
            }

            FilledIconButton(
                onClick = {

                },
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "favorites",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun DescriptionSection(
    modifier: Modifier = Modifier,
    name: String,
    location: String,
    rating: Int,
    totalReviews: Int,
    distance: Int,
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
                    text = "($totalReviews reviews)",
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
                    text = "${distance} km"
                )
                Spacer(modifier = Modifier.width(5.dp))
                IconText(
                    icon = Icons.Outlined.NearMe,
                    text = "${(distance * 1.2).toInt()} min"
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
    onBookChargerClicked: ()->Unit
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
                onBookChargerClicked = { onBookChargerClicked.invoke() }
            )
        }
    }
}

@Composable
fun ReviewHeaderSection(
    modifier: Modifier = Modifier,
    reviews: List<Review>
){
    Row(modifier = modifier.padding(vertical = 10.dp)) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "${reviews.averageRating().toString()}.0",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 30.sp
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Ratings(
                rating = reviews.averageRating(),
                starSize = 27.dp,
                starColor = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "(${reviews.size}) reviews",
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
            RatingBar(
                value = reviews.numberOfReviews(5).toFloat() / reviews.size,
                modifier = Modifier.fillMaxWidth(),
                number = 5
            )
            Spacer(modifier = Modifier.height(5.dp))
            RatingBar(
                value = reviews.numberOfReviews(4).toFloat() / reviews.size,
                modifier = Modifier.fillMaxWidth(),
                number = 4
            )
            Spacer(modifier = Modifier.height(5.dp))
            RatingBar(
                value = reviews.numberOfReviews(3).toFloat() / reviews.size,
                modifier = Modifier.fillMaxWidth(),
                number = 3
            )
            Spacer(modifier = Modifier.height(5.dp))
            RatingBar(
                value = reviews.numberOfReviews(2).toFloat() / reviews.size,
                modifier = Modifier.fillMaxWidth(),
                number = 2
            )
            Spacer(modifier = Modifier.height(5.dp))
            RatingBar(
                value = reviews.numberOfReviews(1).toFloat() / reviews.size,
                modifier = Modifier.fillMaxWidth(),
                number = 1
            )
        }
    }
}

fun List<Review>.numberOfReviews(rating: Int): Int{
    return this.filter { it.rating == rating }.size
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
            ReviewHeaderSection(reviews = reviews)
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

fun List<Review>.averageRating(): Int{
    return this.sumOf { it.rating } / this.size
}
