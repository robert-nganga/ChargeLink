package com.nganga.robert.chargelink.screens.booking_screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.TimeToLeave
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
import com.nganga.robert.chargelink.R
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
    var bookingDate by remember {
        mutableStateOf("")
    }
    var bookingTime by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var durationFormat by remember { mutableStateOf("") }


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
                            singleLine = true
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
                                .fillMaxWidth()
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        timeState.show()
                                    }
                                },
                            label = { Text(text = stringResource(id = R.string.time)) },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.TimeToLeave,
                                    contentDescription = "Time Icon",
                                    modifier = Modifier.clickable {
                                        timeState.show()
                                    }
                                )
                            },
                            singleLine = true
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = stringResource(id = R.string.select_charging_duration),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    BookingSelector(
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(id = R.string.duration),
                        text = duration,
                        onTextChange = {
                            duration = it
                        },
                        selectionChanged = {
                            durationFormat = it
                        },
                        suggestions = listOf("Hours", "Minutes")
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
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

            }
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