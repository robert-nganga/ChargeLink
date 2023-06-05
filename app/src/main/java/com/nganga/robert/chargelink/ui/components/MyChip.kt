package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyChip(
    name: String,
    isSelected: Boolean = false,
    onSelectionChanged: (String)->Unit,
    modifier: Modifier = Modifier
){
    Surface(
        modifier = Modifier.padding(4.dp),
        tonalElevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
    ) {
    Row(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .height(40.dp)
            .toggleable(
                value = isSelected,
                onValueChange = { onSelectionChanged(name) }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (isSelected){
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
            )
        )

      }
    }
}