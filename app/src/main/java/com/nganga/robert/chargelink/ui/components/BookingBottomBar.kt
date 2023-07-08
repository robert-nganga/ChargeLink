package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R


@Composable
fun BooKingBottomBar(
    onBackButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    isNextButtonEnabled: Boolean,
    nextButtonText: String = stringResource(id = R.string.continues),
    backButtonText: String = stringResource(id = R.string.back)
){
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        OutlinedButton(
            onClick = {
                onBackButtonClicked.invoke()
            },
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                text = backButtonText,
            )
        }

        Button(
            onClick = {
                onNextButtonClicked.invoke()
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            enabled = isNextButtonEnabled
        ) {
            Text(
                text = nextButtonText,
            )
        }
    }
}