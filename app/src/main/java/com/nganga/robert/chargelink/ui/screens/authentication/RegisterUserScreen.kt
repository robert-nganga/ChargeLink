package com.nganga.robert.chargelink.ui.screens.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.maxkeppeker.sheets.core.CoreDialog
import com.maxkeppeker.sheets.core.models.CoreSelection
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.ui.components.BoxIcon
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUserScreen(){

    val calendarState = rememberSheetState()

    var name by remember{
        mutableStateOf("")
    }
    var email by remember{
        mutableStateOf("")
    }
    var gender by remember {
        mutableStateOf("")
    }
    var dob by remember {
        mutableStateOf("")
    }

    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date{ date->
            val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
            dob = date.format(formatter)
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            minYear = 1960,
            maxYear = 2008
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        ProfilePhotoSection()
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.name))},
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0x0D000000)
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.email))},
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0x0D000000)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        GenderSelector(
            gender = gender,
            onGenderSelectionChanged = { gender = it }
        )
        Spacer(modifier = Modifier.height(10.dp))
        CompositionLocalProvider(
            LocalTextInputService provides null
        ){
            OutlinedTextField(
                value = dob,
                onValueChange = {dob = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                            if (it.isFocused){
                                calendarState.show()
                            }
                    },
                label = { Text(text = stringResource(id = R.string.date_of_birth))},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0x0D000000)
                ),
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

    }
}

@Composable
fun ProfilePhotoSection(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.size(100.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.user1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(0.9f)
                .aspectRatio(1f, matchHeightConstraintsFirst = true)
                .clip(CircleShape)
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(top = 30.dp)
        ) {
            BoxIcon(
                icon = Icons.Outlined.Edit,
                iconSize = 24.dp,
                iconTint = MaterialTheme.colorScheme.onPrimary,
                background = MaterialTheme.colorScheme.primary
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderSelector(
    modifier: Modifier = Modifier,
    gender: String = "",
    onGenderSelectionChanged: (String)->Unit
){
    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Male","Female")

    var textFieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded)
        Icons.Filled.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.ArrowDropDown


    Box {
        CompositionLocalProvider(
            LocalTextInputService provides null
        ) {
            OutlinedTextField(
                value = gender,
                onValueChange = { onGenderSelectionChanged(it) },
                modifier = modifier
                    .fillMaxWidth()
                    .onFocusChanged { }
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textFieldSize = coordinates.size.toSize()
                    },
                label = { Text(stringResource(id = R.string.gender)) },
                trailingIcon = {
                    Icon(icon,"contentDescription",
                        Modifier.clickable { expanded = !expanded })
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0x0D000000)
                ),
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textFieldSize.width.toDp()})
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                    onGenderSelectionChanged(label)
                },
                    text = {
                        Text(text = label)
                    }
                ) 
            }
        }
    }
}