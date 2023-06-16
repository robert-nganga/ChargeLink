package com.nganga.robert.chargelink.ui.screens.authentication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R

@Composable
fun EnterNumberScreen(){

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterNumberCard(
    modifier: Modifier = Modifier,
    phone: String,
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
            modifier = Modifier.padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(id = R.string.otp_verification),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = stringResource(id = R.string.enter_number_for_verification),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(3.dp))
            TextField(
                value = phone,
                onValueChange = { onPhoneChanged(it) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle()
            )
        }

    }

}