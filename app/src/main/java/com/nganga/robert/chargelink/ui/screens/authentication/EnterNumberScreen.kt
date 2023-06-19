package com.nganga.robert.chargelink.ui.screens.authentication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R

@Composable
fun EnterNumberScreen(
    onContinueClicked: ()->Unit
){
    var phone by remember{
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.padding(vertical = 10.dp),
                    onClick = {  }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(id = R.string.phone_verification),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_otp),
                contentDescription = "OTP Icon",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(30.dp))
            EnterNumberCard(
                phone = phone,
                onPhoneChanged = { phone = it },
                onContinueClicked = {onContinueClicked()}
            )

        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterNumberCard(
    modifier: Modifier = Modifier,
    phone: String,
    onContinueClicked: () -> Unit,
    onPhoneChanged: (String)->Unit
){
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        border = null,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(id = R.string.otp_verification),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.enter_number_for_verification),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                value = phone,
                label = {
                    Text(text = stringResource(id = R.string.phone))
                },
                onValueChange = { onPhoneChanged(it) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(),
                leadingIcon = {
                    Text(text = "+254 ")
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.align(Alignment.End),
                onClick = { onContinueClicked() }
            ) {
                Text(text = stringResource(id = R.string.continues))
            }
        }

    }

}