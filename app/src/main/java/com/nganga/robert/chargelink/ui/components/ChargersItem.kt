package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.models.Charger

@Composable
fun ChargersItem(
    modifier: Modifier,
    charger: Charger
){
   Card(
       shape = RoundedCornerShape(20.dp),
       border = null,
       elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
       modifier = modifier
           .background(MaterialTheme.colorScheme.primary)
           .padding(start = 20.dp)
           .clip(RoundedCornerShape(20.dp))
           .background(MaterialTheme.colorScheme.surface),
   ) {
       Column(
           modifier = Modifier
               .fillMaxWidth()
               .padding(horizontal = 5.dp, vertical = 10.dp),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Top
       ) {
           Row(
               modifier = Modifier.fillMaxWidth(),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.SpaceBetween
           ) {
               Text(
                   text = "24 Hours",
                   style = MaterialTheme.typography.bodyLarge.copy(
                       color = MaterialTheme.colorScheme.outline
                   )
               )
               Text(
                   text = "Available",
                   style = MaterialTheme.typography.bodyLarge.copy(
                       color = MaterialTheme.colorScheme.primary
                   )
               )
           }
           Spacer(modifier = Modifier.height(15.dp))
           Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceAround,
               verticalAlignment = Alignment.CenterVertically
           ) {
               Column {
                   Text(
                       text = charger.plug,
                       style = MaterialTheme.typography.bodyMedium.copy(
                           color = MaterialTheme.colorScheme.outline
                       )
                   )
                   Spacer(modifier = Modifier.height(10.dp))
                   Icon(
                       painter = charger.image,
                       modifier = Modifier.size(30.dp),
                       contentDescription = null,
                   )

               }
               Column {
                   Text(
                       text = "Max Power",
                       style = MaterialTheme.typography.bodyMedium.copy(
                           color = MaterialTheme.colorScheme.outline
                       )
                   )
                   Spacer(modifier = Modifier.height(10.dp))
                   Text(
                       text = charger.power,
                       style = MaterialTheme.typography.titleLarge
                   )

               }

           }
           Spacer(modifier = Modifier.height(15.dp))
           Button(
               onClick = { /*TODO*/ },
               modifier = Modifier.fillMaxWidth(0.7f)
           ) {
               Text(text = "Book")
           }
       }
   }
}