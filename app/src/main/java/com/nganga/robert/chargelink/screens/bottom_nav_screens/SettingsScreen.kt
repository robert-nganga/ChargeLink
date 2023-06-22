package com.nganga.robert.chargelink.screens.bottom_nav_screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R

@Composable
fun SettingsScreen(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        IconButton(
            onClick = { }
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
            trailingText = "Dark Mode"
        )
        Spacer(modifier = Modifier.height(10.dp))
        SettingsItem(
            icon = Icons.Outlined.ShareLocation,
            headerText = stringResource(id = R.string.nearby_radius),
            trailingText = "10 km"
        )
        Spacer(modifier = Modifier.height(10.dp))
        SettingsItem(
            icon = Icons.Outlined.Language,
            headerText = stringResource(id = R.string.language),
            trailingText = "English"
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
            trailingText = stringResource(id = R.string.invite_friends)
        )
        Spacer(modifier = Modifier.height(10.dp))
        SettingsItem(
            icon = Icons.Outlined.BugReport,
            headerText = stringResource(id = R.string.report_issue),
            trailingText = stringResource(id = R.string.improve_charge_link)
        )
        Spacer(modifier = Modifier.height(10.dp))
        SettingsItem(
            icon = Icons.Outlined.StarRate,
            headerText = stringResource(id = R.string.rate_us),
            trailingText = stringResource(id = R.string.share_feedback)
        )
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    headerText: String,
    trailingText:String,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier.padding(horizontal = 10.dp),
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