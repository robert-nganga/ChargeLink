package com.nganga.robert.chargelink.screens.booking_screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.EvStation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.PaymentMethod
import com.nganga.robert.chargelink.ui.components.BooKingBottomBar
import com.nganga.robert.chargelink.utils.IconUtils
import com.nganga.robert.chargelink.utils.TimeUtils.getDurationString


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookingConfirmationScreen(
    onBackButtonClicked: () -> Unit,
    onConfirmButtonClicked: () -> Unit,
    bookingViewModel: BookingViewModel
) {

    val bookingDetails = bookingViewModel.booking

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            stickyHeader {
                BookingConfirmationScreenTopBar(
                    modifier = Modifier.fillMaxWidth(),
                    onBackButtonClicked = {
                        onBackButtonClicked.invoke()
                    }
                )
            }
            item{ Spacer(modifier = Modifier.height(10.dp)) }
            item{
                ItemSlot(
                    title = stringResource(id = R.string.charging_station),
                    modifier = Modifier.padding(horizontal = 10.dp)
                ){
                    ChargingStationItem(
                        name = bookingDetails.stationName,
                        location = bookingDetails.stationLocation,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            item{
                ItemSlot (
                    title = stringResource(id = R.string.charger),
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    ChargerItem(
                        plug = bookingDetails.charger.plug,
                        power = bookingDetails.charger.power,
                    )

                }
            }
            item{
                ItemSlot(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ){
                    BookingDetailsItem(
                        date = bookingDetails.date,
                        arrivalTime = bookingDetails.time,
                        chargingDuration = bookingDetails.duration.getDurationString(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            item{
                ItemSlot(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ){
                    PriceDetailsItem(
                        price = bookingDetails.totalPrice,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            item{
                ItemSlot(
                    title = stringResource(id = R.string.selected_payment_method),
                    modifier = Modifier.padding(horizontal = 10.dp)
                ){
                    PaymentMethodItem(
                        paymentMethod = bookingViewModel.paymentMethod,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

        }

        BooKingBottomBar(
            onBackButtonClicked = {
                onBackButtonClicked.invoke()
            },
            onNextButtonClicked = {
                onConfirmButtonClicked.invoke()
            },
            isNextButtonEnabled = true,
            nextButtonText = stringResource(id = R.string.confirm)
        )

    }

}

@Composable
fun BookingConfirmationScreenTopBar(
    modifier: Modifier = Modifier,
    title: String = "Payment Details",
    onBackButtonClicked: () -> Unit
) {
    Row(
        modifier = modifier.padding(top = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            onClick = {
                onBackButtonClicked.invoke()
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ItemSlot(
    modifier: Modifier = Modifier,
    title: String? = null,
    item: @Composable () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        if (title != null){
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        Card(
            modifier = modifier.padding(bottom = 10.dp),
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            item.invoke()
        }
    }
}

@Composable
fun ChargerItem(
    plug: String,
    power: String,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ){
        ChargerTextColumn(
            modifier = Modifier.weight(1f),
            headerText = plug,
            trailingText = "",
            icon = painterResource(id = IconUtils.getChargerIcon(plug)),
            iconTint = MaterialTheme.colorScheme.primary
        )
        ChargerTextColumn(
            modifier = Modifier.weight(1f),
            headerText = stringResource(id = R.string.max_power),
            trailingText = power,
        )
    }
}

@Composable
fun BookingDetailsItem(
    modifier: Modifier = Modifier,
    date: String,
    arrivalTime: String,
    chargingDuration: String
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 15.dp),
    ) {
        RowItem(
            title = stringResource(id = R.string.booking_date),
            text = date
        )
        Spacer(modifier = Modifier.height(10.dp))
        RowItem(
            title = stringResource(id = R.string.time_of_arrival),
            text = arrivalTime
        )
        Spacer(modifier = Modifier.height(10.dp))
        RowItem(
            title = stringResource(id = R.string.charging_duration),
            text = chargingDuration
        )

    }
}

@Composable
fun PriceDetailsItem(
    price: String,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 15.dp),
    ) {
        RowItem(
            title = stringResource(id = R.string.cost),
            text = "Ksh $price.00"
        )
        Spacer(modifier = Modifier.height(10.dp))
        RowItem(
            title = stringResource(id = R.string.tax),
            text = "Ksh 0.00"
        )
        Spacer(modifier = Modifier.height(10.dp))
        RowItem(
            title = stringResource(id = R.string.total_amount),
            text = "Ksh $price.00"
        )
    }
}

@Composable
fun PaymentMethodItem(
    paymentMethod: PaymentMethod,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        if (paymentMethod.icon != null) {
            Icon(
                imageVector = paymentMethod.icon,
                contentDescription = paymentMethod.title,
                modifier = modifier
                    .size(40.dp)
                    .padding(5.dp)
            )
        }
        if (paymentMethod.image != null) {
            Image(
                painter = paymentMethod.image,
                contentDescription = paymentMethod.title,
                modifier = modifier
                    .size(50.dp)
                    .padding(5.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = paymentMethod.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier
                .padding(2.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

    }
}

@Composable
fun RowItem(
    modifier: Modifier = Modifier,
    title: String,
    text: String
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.outline
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ChargingStationItem(
    modifier: Modifier = Modifier,
    name: String,
    location: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = Icons.Outlined.EvStation,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(40.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = location,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
