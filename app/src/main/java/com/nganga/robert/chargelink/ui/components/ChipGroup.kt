package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChipGroup(
    names: List<String>,
    modifier: Modifier = Modifier,
    selectedItem: String,
    onSelectionChange: (String)->Unit
){
    LazyRow(modifier = modifier){
        items(names){name->
            MyChip(
                name = name,
                isSelected = selectedItem == name,
                onSelectionChanged = { onSelectionChange(it) }
            )
        }
    }



}