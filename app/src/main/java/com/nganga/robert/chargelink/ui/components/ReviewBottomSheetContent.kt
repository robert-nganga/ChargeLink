package com.nganga.robert.chargelink.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
    var newRating by remember{
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
            Spacer(modifier = Modifier.height(15.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.give_it_a_star),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(20.dp))
            Ratings(
                rating = newRating,
                starSize = 40.dp,
                starColor = MaterialTheme.colorScheme.primary,
                onRatingChanged = {
                    newRating = it
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.comment),
                modifier = Modifier.align(Alignment.Start),
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = message,
                onValueChange = { comment-> 
                    message = comment
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent
                ),
                singleLine = false,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(15.dp))
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