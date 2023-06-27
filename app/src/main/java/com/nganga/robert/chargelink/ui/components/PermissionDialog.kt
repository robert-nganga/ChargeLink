package com.nganga.robert.chargelink.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nganga.robert.chargelink.R


@Composable
fun PermissionDialog(
    text: String,
    onDismiss: () -> Unit,
    onOkayClick: () -> Unit,
){
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onOkayClick
            ) {
                Text(
                    text = stringResource(id = R.string.ok)
                )
            }
        },
        title = {
            Text(text = stringResource(id = R.string.permission_required))
        },
        text = {
            Text(text = text)
        }
    )
}
