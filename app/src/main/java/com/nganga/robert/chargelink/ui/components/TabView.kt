package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.TabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TabView(
    modifier: Modifier = Modifier,
    tabTitles: List<String>,
    onTabSelected: (selectedIndex: Int) -> Unit
){
    var selectedTabIndex by rememberSaveable{
        mutableStateOf(0)
    }

    TabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = MaterialTheme.colorScheme.primary,
        backgroundColor = Color.Transparent,
        modifier = modifier

    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                selectedContentColor = MaterialTheme.colorScheme.primary ,
                unselectedContentColor = MaterialTheme.colorScheme.outline,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(index)
                },
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = if (selectedTabIndex == index)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.outline
                    )
                )
            }
        }
    }
}