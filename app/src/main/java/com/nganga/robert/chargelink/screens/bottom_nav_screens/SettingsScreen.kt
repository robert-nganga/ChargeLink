package com.nganga.robert.chargelink.screens.bottom_nav_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.ui.theme.UserPreferencesViewModel
import com.nganga.robert.chargelink.utils.ThemeSelection

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    userPreferencesViewModel: UserPreferencesViewModel
){
    val preferences = userPreferencesViewModel.userPreferences.observeAsState()


    var isThemeDialogVisible by remember {
        mutableStateOf(false)
    }

    var isRadiusDialogVisible by remember {
        mutableStateOf(false)
    }

    var radius by remember {
        mutableStateOf("${preferences.value?.radius ?: 18} km")
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        if (isThemeDialogVisible){
            ThemeSelectionDialog(
                choices = ThemeSelection.selections,
                selected = preferences.value?.appTheme ?: ThemeSelection.USE_SYSTEM_SETTINGS,
                onDismiss = {
                   isThemeDialogVisible = false
                },
                onSelectionChange = {
                    isThemeDialogVisible = false
                    userPreferencesViewModel.themeSelectionChange(it)
                }
            )
        }
        if (isRadiusDialogVisible){
            ChangeRadiusDialog(
                onDismiss = {
                    isRadiusDialogVisible = false
                },
                radius = radius,
                onConfirmClicked = { selectedRadius ->
                    val radiusF = selectedRadius.substringBefore("km").trim().toFloat()
                    userPreferencesViewModel.changeRadius(radiusF)
                    radius = selectedRadius
                    isRadiusDialogVisible = false
                }
            )
        }
        IconButton(
            onClick = {
                onBackClick.invoke()
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(id = R.string.general_settings),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        SettingsItem(
            icon = Icons.Outlined.Palette,
            headerText = stringResource(id = R.string.theme),
            trailingText = "Dark Mode",
            onItemClick = {
                isThemeDialogVisible = true
            }
        )
        SettingsItem(
            icon = Icons.Outlined.ShareLocation,
            headerText = stringResource(id = R.string.nearby_radius),
            trailingText = radius,
            onItemClick = {
                isRadiusDialogVisible = true
            }
        )
        SettingsItem(
            icon = Icons.Outlined.Language,
            headerText = stringResource(id = R.string.language),
            trailingText = "English",
            onItemClick = { }
        )

        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = stringResource(id = R.string.other_settings),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        SettingsItem(
            icon = Icons.Outlined.Share,
            headerText = stringResource(id = R.string.share_us),
            trailingText = stringResource(id = R.string.invite_friends),
            onItemClick = { }
        )
        SettingsItem(
            icon = Icons.Outlined.BugReport,
            headerText = stringResource(id = R.string.report_issue),
            trailingText = stringResource(id = R.string.improve_charge_link),
            onItemClick = { }
        )
        SettingsItem(
            icon = Icons.Outlined.StarRate,
            headerText = stringResource(id = R.string.rate_us),
            trailingText = stringResource(id = R.string.share_feedback),
            onItemClick = { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeRadiusDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    radius: String,
    onConfirmClicked: (String) -> Unit
){
    var myRadius by remember {
        mutableStateOf(radius)
    }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = modifier
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = R.string.change_radius),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                RadiusSelector(
                    radius = myRadius,
                    onRadiusSelectionChanged = {
                       myRadius = it
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    TextButton(
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    TextButton(
                        onClick = {
                            onConfirmClicked(myRadius)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.confirm),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

            }

        }
    }
}

@Composable
fun ThemeSelectionDialog(
    choices: List<String>,
    selected: String,
    onDismiss: () -> Unit,
    onSelectionChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    var selectedChoice by remember { mutableStateOf(selected) }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = modifier
                .padding(16.dp)
        ) {
            Column(
                modifier = modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.change_app_theme),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                choices.forEach { choice ->
                    ThemeItem(
                        onItemClick = {
                            selectedChoice = choice
                        },
                        selected = choice == selectedChoice,
                        text = choice
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(horizontal = 10.dp)
                ) {
                    TextButton(
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    TextButton(
                        onClick = {
                            onSelectionChange(selectedChoice)
                            onDismiss()
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.confirm),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeItem(
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean,
    text: String
){
    Row(
        modifier = modifier
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = selected,
            onClick = { onItemClick() }
        )
        Box(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )

    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    headerText: String,
    trailingText:String,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(28.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column {
            Text(
                text = headerText,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = trailingText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.outline,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadiusSelector(
    modifier: Modifier = Modifier,
    radius: String = "",
    onRadiusSelectionChanged: (String)->Unit
){
    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("5 km", "10 km", "15 km", "20km", "25km", "30km")

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.ArrowDropUp
    else
        Icons.Filled.ArrowDropDown


    Box(modifier = modifier) {
        CompositionLocalProvider(
            LocalTextInputService provides null
        ) {
            TextField(
                value = radius,
                onValueChange = { onRadiusSelectionChanged(it) },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .onFocusChanged {
                        if (it.isFocused) {
                            expanded = !expanded
                        }
                    }
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textFieldSize = coordinates.size.toSize()
                    },
                trailingIcon = {
                    Icon(icon,"contentDescription",
                        Modifier.clickable { expanded = !expanded })
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
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
                        onRadiusSelectionChanged(label)
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