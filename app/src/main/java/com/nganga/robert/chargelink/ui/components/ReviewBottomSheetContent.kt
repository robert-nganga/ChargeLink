package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewBottomSheetContent(
    modifier: Modifier = Modifier,
    onRatingChanged: (Int, String)->Unit,
    rating: Int
){
    var message by rememberSaveable{
        mutableStateOf("")
    }
    var newRating by rememberSaveable{
        mutableStateOf(rating)
    }

    Box(
        modifier = modifier.clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.write_a_review),
                style = MaterialTheme.typography.titleMedium
            )
            HorizontalDivider()
            Text(
                text = stringResource(id = R.string.give_it_a_star),
                style = MaterialTheme.typography.titleMedium
            )
            Ratings(
                rating = newRating,
                starSize = 40.dp,
                starColor = MaterialTheme.colorScheme.primary,
                onRatingChanged = {
                    newRating = it
                }
            )
            HorizontalDivider()
            Text(
                text = stringResource(id = R.string.comment),
                modifier = Modifier.align(Alignment.Start),
                style = MaterialTheme.typography.titleSmall
            )
            TextField(
                value = message,
                onValueChange = { comment-> 
                    message = comment
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent
                ),
                singleLine = false
            )
            HorizontalDivider()
            Button(
                onClick = {
                    onRatingChanged(newRating, message)
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(text = stringResource(id = R.string.submit))
            }
        }
    }
}