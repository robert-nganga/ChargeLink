package com.nganga.robert.chargelink.screens.bottom_nav_screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.models.Booking
import com.nganga.robert.chargelink.screens.bottom_nav_screens.booking_history_screen.BookingHistoryState
import com.nganga.robert.chargelink.screens.bottom_nav_screens.booking_history_screen.BookingHistoryViewModel
import com.nganga.robert.chargelink.ui.components.BookingItem
import com.nganga.robert.chargelink.ui.components.TabView

@Composable
fun BookingsScreen(
    onBackPressed: () -> Unit,
    viewModel: BookingHistoryViewModel,
    onBookingClicked: (String) -> Unit
){
    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }

    val completedBookings = viewModel.completedBookings.collectAsState(
        initial = BookingHistoryState()
    )
    val pendingBookings = viewModel.pendingBookings.collectAsState(
        initial = BookingHistoryState()
    )
    val canceledBookings = viewModel.canceledBookings.collectAsState(
        initial = BookingHistoryState()
    )

    LaunchedEffect(key1 = true){
        viewModel.getUserBookings()
    }

    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        BookingsTopAppBar(
            onBackPressed = onBackPressed,
            title = "My Bookings"
        )
        Spacer(modifier = Modifier.height(10.dp))
        TabView(
            tabTitles = listOf("Pending", "Completed", "Canceled"),
            onTabSelected = { selectedTabIndex = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        when (selectedTabIndex){
            0 -> {
                BookingsListSection(
                    modifier = Modifier.fillMaxSize(),
                    bookings = pendingBookings.value.bookings,
                    onBookingClicked = onBookingClicked
                )
            }
            1 -> {
                BookingsListSection(
                    modifier = Modifier.fillMaxSize(),
                    bookings = completedBookings.value.bookings,
                    onBookingClicked = onBookingClicked
                )
            }
            else -> {
                BookingsListSection(
                    modifier = Modifier.fillMaxSize(),
                    bookings = canceledBookings.value.bookings,
                    onBookingClicked = onBookingClicked
                )
            }
        }
    }
}

@Composable
fun BookingsListSection(
    bookings: List<Booking>,
    modifier: Modifier = Modifier,
    onBookingClicked: (String) -> Unit
){
    LazyColumn(
        modifier = modifier
    ){
        items(bookings){ booking ->
            BookingItem(
                booking = booking,
                modifier = Modifier.fillMaxWidth(),
                onBookingClicked = {
                    onBookingClicked(booking.bookingId)
                }
            )
        }
        item{
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}

@Composable
fun BookingsTopAppBar(
    onBackPressed: ()->Unit,
    title: String,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier.padding(vertical = 15.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            onClick =  onBackPressed
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        Box(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {  }
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search Icon"
            )
        }
    }
}