package com.nganga.robert.chargelink.ui.screens.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.ui.components.BoxIcon

@Composable
fun RegisterUserScreen(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        ProfilePhotoSection()
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