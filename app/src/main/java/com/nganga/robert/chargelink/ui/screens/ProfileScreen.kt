package com.nganga.robert.chargelink.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.ui.components.HorizontalDivider
import com.nganga.robert.chargelink.ui.components.IconText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
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
            modifier = Modifier.padding(contentPadding)
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
        HorizontalDivider()
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

