package com.nganga.robert.chargelink.screens.bottom_nav_screens.profile_screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.screens.bottom_nav_screens.home_screen.HomeScreenViewModel
import com.nganga.robert.chargelink.ui.components.HorizontalDivider
import com.nganga.robert.chargelink.ui.components.IconText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onSettingsClick: () -> Unit,
    profileViewModel: ProfileViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    onLogOut: () -> Unit
){

    val user = homeScreenViewModel.homeScreenState.currentUser
    var logOut by remember{
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                        Text(text = stringResource(id = R.string.profile))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {  }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {  }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "more options"
                        )
                    }
                }
            )
        }

    ) { contentPadding->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
        ) {
            if (logOut){
                ConfirmLogOutDialog(
                    onDismiss = {
                        logOut = false
                    },
                    onConfirm = {
                        profileViewModel.logOut()
                        onLogOut.invoke()
                    }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            ProfileSection(
                name = user.name,
                email = user.email,
                phone = user.phone,
                location = homeScreenViewModel.userAddress.observeAsState().value ?: "",
                image = user.imageUrl
            )
            Spacer(modifier = Modifier.height(20.dp))
            ProfileCategoryItem(
                leadingIcon = Icons.Outlined.DirectionsCar,
                text = stringResource(id = R.string.my_vehicles),
                onCategoryClick = {}
            )
            ProfileCategoryItem(
                leadingIcon = Icons.Outlined.Settings,
                text = stringResource(id = R.string.settings),
                onCategoryClick =  onSettingsClick
            )
            ProfileCategoryItem(
                leadingIcon = Icons.Outlined.CreditCard,
                text = stringResource(id = R.string.payment_methods),
                onCategoryClick = {}
            )
            Spacer(modifier = Modifier.height(20.dp))
            ProfileCategoryItem(
                leadingIcon = Icons.Outlined.Info,
                text = stringResource(id = R.string.about_charge_link),
                onCategoryClick = {}
            )
            ProfileCategoryItem(
                leadingIcon = Icons.Outlined.Logout,
                text = stringResource(id = R.string.logout),
                onCategoryClick = {
                    logOut = true
                }
            )
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

@Composable
fun ConfirmLogOutDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
androidx.compose.material.AlertDialog(
        backgroundColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.log_out),
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.are_you_sure_you_want_to_logout),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = stringResource(id = R.string.yes)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = stringResource(id = R.string.no)
                )
            }
        }
    )
}

@Composable
fun ProfileSection(
    name: String,
    email: String,
    phone: String,
    location: String,
    image: String,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundProfileImage(
                image = image,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.outline
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        IconText(
            icon = Icons.Outlined.Phone,
            text = "    $phone",
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.outline
            ),
            iconTint = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(10.dp))
        IconText(
            icon = Icons.Outlined.Email,
            text = "    $email",
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.outline
            ),
            iconTint = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(0.9f))
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RoundProfileImage(
    image: String,
    modifier: Modifier = Modifier
){
    GlideImage(
        model = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .padding(2.dp)
            .clip(CircleShape)
    )
}

@Composable
fun ProfileCategoryItem(
    leadingIcon: ImageVector,
    text: String,
    onCategoryClick: ()->Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .clickable { onCategoryClick() },
    ) {
        Row(
            modifier = Modifier.padding(vertical = 15.dp, horizontal = 5.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconText(
                icon = leadingIcon,
                text = "   $text",
                textStyle = MaterialTheme.typography.titleMedium,
                iconTint = MaterialTheme.colorScheme.primary
            )
            Box(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = text
            )
        }
    }
}

