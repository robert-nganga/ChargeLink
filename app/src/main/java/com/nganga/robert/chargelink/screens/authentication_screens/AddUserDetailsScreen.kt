package com.nganga.robert.chargelink.screens.authentication_screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.ui.components.BooKingBottomBar
import com.nganga.robert.chargelink.ui.components.ProgressDialog
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUserScreen(
    viewModel: AuthenticationViewModel,
    onContinueClicked: ()->Unit
){

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            viewModel.uploadProfilePhoto(uri)
        }
    }

    val state = viewModel.addUserDetailsState

    val calendarState = rememberSheetState()

    var name by remember {
        mutableStateOf("")
    }
    var phone by remember {
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
            val dateOfBirth = date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
            dob = dateOfBirth
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            minYear = 1960,
            maxYear = 2008
        )
    )

    LaunchedEffect(key1 = state.isUserAddedSuccessfully){
        if (state.isUserAddedSuccessfully){
            onContinueClicked.invoke()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            RegisterScreenTopAppBar(
                title = stringResource(id = R.string.complete_profile),
                modifier = Modifier.fillMaxWidth()
            )
            ProfilePhotoSection(
                onEditProfilePhotoClicked = {
                    galleryLauncher.launch("image/*")
                },
                isLoading = viewModel.profilePhotoState.isLoading
            )
            if (state.isLoading) {
                ProgressDialog(
                    text = stringResource(id = R.string.please_wait),
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                shape = RoundedCornerShape(10.dp),
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(id = R.string.name))},
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                shape = RoundedCornerShape(10.dp),
                value = phone,
                onValueChange = { phone = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(id = R.string.phone))},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            GenderSelector(
                gender = gender,
                onGenderSelectionChanged = { gender = it },
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(10.dp))
            CompositionLocalProvider(
                LocalTextInputService provides null
            ){
                OutlinedTextField(
                    shape = RoundedCornerShape(10.dp),
                    value = dob,
                    onValueChange = {dob = it},
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            if (it.isFocused) {
                                calendarState.show()
                            }
                        },
                    label = { Text(text = stringResource(id = R.string.date_of_birth))},
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
            Spacer(modifier = Modifier.height(10.dp))
            if (state.isError){
                Text(
                    text = state.errorMsg,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
        }

        BooKingBottomBar(
            onBackButtonClicked = {},
            onNextButtonClicked = {
                viewModel.onSubmitUserDetailsClicked(name, phone, gender, dob)
            },
            isNextButtonEnabled = true,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            nextButtonText = stringResource(id = R.string.submit),
            backButtonText = stringResource(id = R.string.skip)
        )

    }

}

@Composable
fun ProfilePhotoSection(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    onEditProfilePhotoClicked: ()->Unit,
    isLoading: Boolean = false
){
    Box(
        modifier = modifier.size(120.dp)
    ) {
        if (isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize(0.9f)
                    .aspectRatio(1f, matchHeightConstraintsFirst = true)
                    .clip(CircleShape)
                    .background(Color(0x0D000000))
            ) {
                CircularProgressIndicator(
                    strokeWidth = 5.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.profile),
            contentDescription = "profile photo",
            contentScale = ContentScale.Crop,
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
            FilledIconButton(
                onClick = {
                    onEditProfilePhotoClicked.invoke()
                },
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "edit profile"
                )
            }
        }

    }
}

@Composable
fun RegisterScreenTopAppBar(
    modifier: Modifier = Modifier,
    title: String
){
    Row(
        modifier = modifier.padding(vertical = 8.dp),
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


    Box(modifier = modifier) {
        CompositionLocalProvider(
            LocalTextInputService provides null
        ) {
            OutlinedTextField(
                shape = RoundedCornerShape(10.dp),
                value = gender,
                onValueChange = { onGenderSelectionChanged(it) },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .onFocusChanged {
                        if (it.isFocused) {
                            expanded = !expanded
                        }
                    }
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textFieldSize = coordinates.size.toSize()
                    },
                label = { Text(stringResource(id = R.string.gender)) },
                trailingIcon = {
                    Icon(icon,"contentDescription",
                        Modifier.clickable { expanded = !expanded })
                }
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