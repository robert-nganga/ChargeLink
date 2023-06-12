package com.nganga.robert.chargelink.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.ui.components.HorizontalDivider
import com.nganga.robert.chargelink.ui.components.IconText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onSettingsClick: () -> Unit
){
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
            Spacer(modifier = Modifier.height(10.dp))
            ProfileSection(
                name = "Suzzie Mutiambai",
                email = "suzziemut@gmail.com",
                phone ="+254797044599",
                location = "Westlands, Nairobi",
                image = painterResource(
                    id = R.drawable.user5
                )
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
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

@Composable
fun ProfileSection(
    name: String,
    email: String,
    phone: String,
    location: String,
    image: Painter,
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
        HorizontalDivider(modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.9f))
    }
}

@Composable
fun RoundProfileImage(
    image: Painter,
    modifier: Modifier = Modifier
){
    Image(
        painter = image,
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

