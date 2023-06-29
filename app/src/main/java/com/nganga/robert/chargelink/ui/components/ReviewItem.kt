package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.Review

@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    review: Review
){
    Column(
        modifier = modifier.padding(bottom = 20.dp, top = 10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.user4),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(60.dp)
                    .aspectRatio(1f, matchHeightConstraintsFirst = true)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Text(
                    text = review.userName,
                    style = MaterialTheme.typography.titleMedium
                )
                Ratings(
                    rating = review.rating,
                    starSize = 24.dp,
                    starColor = MaterialTheme.colorScheme.primary
                )
            }
            Box(modifier = Modifier.weight(1f))
            Column {
                Text(
                    text = review.date,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = review.time,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = review.message,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}