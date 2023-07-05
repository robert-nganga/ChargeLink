package com.nganga.robert.chargelink.screens.booking_screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.maxkeppeler.sheets.duration.DurationDialog
import com.maxkeppeler.sheets.duration.models.DurationConfig
import com.maxkeppeler.sheets.duration.models.DurationFormat
import com.maxkeppeler.sheets.duration.models.DurationSelection
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.ui.components.BooKingBottomBar
import com.nganga.robert.chargelink.utils.TimeUtils.getDurationString
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterBookingDetailsScreen(
    onBackButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    bookingViewModel: BookingViewModel
){

    val calendarState = rememberSheetState()
    val timeState = rememberSheetState()
    val durationState = rememberSheetState()

    val booking = bookingViewModel.booking

    var bookingDate by remember {
        mutableStateOf(booking.date)
    }
    var bookingTime by remember { mutableStateOf(booking.time) }
    var duration by remember { mutableStateOf(booking.duration) }

    DurationDialog(
        state = durationState,
        selection = DurationSelection { time ->
            duration = time
        },
        config = DurationConfig(
            displayClearButton = true,
            timeFormat = DurationFormat.HH_MM
        )
    )

    ClockDialog(
        state = timeState,
        selection = ClockSelection.HoursMinutes{ hours, minutes ->
            val selectedTime = "$hours:$minutes"
            bookingTime = selectedTime
        },
        config = ClockConfig(
            is24HourFormat = true
        )
    )

    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date{ date->
            val selectedDate = date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
            bookingDate = selectedDate
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            minYear = 1960,
            maxYear = 2008
        )
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            EnterBookingDetailsScreenTopAppBar(
                title = stringResource(id = R.string.enter_booking_details),
                modifier = Modifier
                    .fillMaxWidth(),
                onBackButtonClicked = {
                    onBackButtonClicked.invoke()
                }
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.08f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.select_date),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CompositionLocalProvider(
                        LocalTextInputService provides null
                    ) {
                        OutlinedTextField(
                            value = bookingDate,
                            onValueChange = { bookingDate = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        calendarState.show()
                                    }
                                },
                            label = { Text(text = stringResource(id = R.string.date)) },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.CalendarMonth,
                                    contentDescription = "Calendar Icon",
                                    modifier = Modifier.clickable {
                                        calendarState.show()
                                    }
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = stringResource(id = R.string.select_arrival_time),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    CompositionLocalProvider(
                        LocalTextInputService provides null
                    ) {
                        OutlinedTextField(
                            value = bookingTime,
                            onValueChange = { bookingTime = it },
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        timeState.show()
                                    }
                                },
                            label = { Text(text = stringResource(id = R.string.time)) },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Schedule,
                                    contentDescription = "Time Icon",
                                    modifier = Modifier.clickable {
                                        timeState.show()
                                    }
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = stringResource(id = R.string.select_charging_duration),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    CompositionLocalProvider(
                        LocalTextInputService provides null
                    ) {
                        OutlinedTextField(
                            value = duration.getDurationString(),
                            onValueChange = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        durationState.show()
                                    }
                                },
                            label = { Text(text = stringResource(id = R.string.duration)) },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Timer,
                                    contentDescription = "Time Icon",
                                    modifier = Modifier.clickable {
                                        durationState.show()
                                    }
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(20.dp)
                        )
                    }
                }
            }
        }
        BooKingBottomBar(
            onBackButtonClicked = {
                onBackButtonClicked.invoke()
            },
            onNextButtonClicked = {
                bookingViewModel.setBookingDetails(
                    date = bookingDate,
                    time = bookingTime,
                    duration = duration
                )
                onNextButtonClicked.invoke()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            isNextButtonEnabled = bookingDate.isNotEmpty() &&
                    bookingTime.isNotEmpty() && duration != 0L
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingSelector(
    modifier: Modifier = Modifier,
    label: String,
    text: String = "",
    onTextChange: (String) -> Unit,
    selectionChanged: (String)->Unit,
    suggestions: List<String>
){
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero)}

    var selection by remember {
        mutableStateOf(suggestions.first())
    }

    val icon = if (expanded)
        Icons.Filled.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.ArrowDropDown


    Box(modifier = modifier) {
        OutlinedTextField(
            value = text,
            onValueChange = { onTextChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused) {
                        expanded = !expanded
                    }
                }
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textFieldSize = coordinates.size.toSize()
                },
            label = { Text(label) },
            trailingIcon = {
                Row {
                    Text(text = selection)
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(icon,"contentDescription",
                        Modifier.clickable { expanded = !expanded })
                }

            },
            shape = RoundedCornerShape(20.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textFieldSize.width.toDp()})
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        selection = label
                        selectionChanged(label)
                        expanded = false
                    },
                    text = {
                        Text(text = label)
                    }
                )
            }
        }
    }
}

@Composable
fun EnterBookingDetailsScreenTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit
){
    Row(
        modifier = modifier.padding(top = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            onClick = {
                onBackButtonClicked.invoke()
            }
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