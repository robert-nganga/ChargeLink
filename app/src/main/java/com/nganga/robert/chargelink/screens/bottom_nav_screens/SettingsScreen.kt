package com.nganga.robert.chargelink.screens.bottom_nav_screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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

    Column(
        modifier = Modifier.fillMaxSize(),
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
        Spacer(modifier = Modifier.height(10.dp))
        SettingsItem(
            icon = Icons.Outlined.Palette,
            headerText = stringResource(id = R.string.theme),
            trailingText = "Dark Mode",
            onItemClick = {
                isThemeDialogVisible = true
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        SettingsItem(
            icon = Icons.Outlined.ShareLocation,
            headerText = stringResource(id = R.string.nearby_radius),
            trailingText = "10 km",
            onItemClick = { }
        )
        Spacer(modifier = Modifier.height(10.dp))
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
        Spacer(modifier = Modifier.height(10.dp))
        SettingsItem(
            icon = Icons.Outlined.Share,
            headerText = stringResource(id = R.string.share_us),
            trailingText = stringResource(id = R.string.invite_friends),
            onItemClick = { }
        )
        Spacer(modifier = Modifier.height(10.dp))
        SettingsItem(
            icon = Icons.Outlined.BugReport,
            headerText = stringResource(id = R.string.report_issue),
            trailingText = stringResource(id = R.string.improve_charge_link),
            onItemClick = { }
        )
        Spacer(modifier = Modifier.height(10.dp))
        SettingsItem(
            icon = Icons.Outlined.StarRate,
            headerText = stringResource(id = R.string.rate_us),
            trailingText = stringResource(id = R.string.share_feedback),
            onItemClick = { }
        )
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

    androidx.compose.material.AlertDialog(
        onDismissRequest = { onDismiss() },
        backgroundColor = MaterialTheme.colorScheme.background,
        title = {
            Text(
                text = stringResource(id = R.string.change_app_theme),
                style = MaterialTheme.typography.titleLarge
            )
        },
        confirmButton = {
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
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        text = {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                choices.forEach { choice ->
                    ThemeItem(
                        onItemClick = {
                            selectedChoice = choice
                        },
                        selected = choice == selectedChoice,
                        text = choice
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    )
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
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = selected,
            onClick = { onItemClick() }
        )
        Box(modifier = Modifier.width(20.dp))
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
            .padding(horizontal = 10.dp),
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
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = trailingText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )
        }
    }
}