package com.nganga.robert.chargelink.screens.booking_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R


@Composable
fun PaymentDetailsScreen() {
    var selectedTab by rememberSaveable{
        mutableStateOf("")
    }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PaymentDetailsTopBar(
                modifier = Modifier.fillMaxWidth(),
                onBackButtonClicked = {},
                title = stringResource(id = R.string.select_payment_method)
            )
            Spacer(modifier = Modifier.height(20.dp))
            PaymentMethodTabView(
                modifier = Modifier.fillMaxWidth(),
                tabs = listOf(
                    PaymentMethod(
                        title = stringResource(id = R.string.card),
                        icon = Icons.Outlined.CreditCard
                    ),
                    PaymentMethod(
                        title = stringResource(id = R.string.paypal),
                        image = painterResource(id = R.drawable.paypal)
                    ),
                    PaymentMethod(
                        title = "M-Pesa",
                        image = painterResource(id = R.drawable.mpesa)
                    ),
                    PaymentMethod(
                        title = stringResource(id = R.string.pay_on_location),
                        icon = Icons.Outlined.Payments
                    ),
                ),
                onTabSelected = { selectedTab = it },
                selectedTab = selectedTab,
                width = screenWidth * 0.245f
            )

        }
    }
}

@Composable
fun PaymentMethodTabView(
    modifier: Modifier = Modifier,
    tabs: List<PaymentMethod>,
    onTabSelected: (String) -> Unit,
    selectedTab: String,
    width: Dp
){

    Row(
        modifier = modifier.padding(8.dp)
    ){
        tabs.forEach{tab->
            PaymentMethodItem(
                modifier = Modifier.width(width),
                paymentMethod = tab,
                isSelected = tab.title == selectedTab,
                onPaymentMethodSelected = {
                    onTabSelected.invoke(tab.title)
                }
            )
        }
    }
}

@Composable
fun PaymentMethodItem(
    modifier: Modifier = Modifier,
    paymentMethod: PaymentMethod,
    isSelected: Boolean,
    onPaymentMethodSelected: () -> Unit
){
    Column(
        modifier = modifier
            .padding(5.dp)
            .border(
                width = 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                onPaymentMethodSelected.invoke()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if (paymentMethod.icon != null) {
            Icon(
                imageVector = paymentMethod.icon,
                contentDescription = paymentMethod.title,
                modifier = modifier
                    .size(50.dp)
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
        Text(
            text = paymentMethod.title,
            style = MaterialTheme.typography.labelMedium,
            modifier = modifier.padding(2.dp),
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun PaymentDetailsTopBar(
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

data class PaymentMethod(
    val icon: ImageVector? = null,
    val title: String,
    val image: Painter? = null
)