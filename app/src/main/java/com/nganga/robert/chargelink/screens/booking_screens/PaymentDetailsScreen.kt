package com.nganga.robert.chargelink.screens.booking_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.ui.components.BooKingBottomBar


@Composable
fun PaymentDetailsScreen(
    onBackButtonClicked: () -> Unit,
    onContinueClicked: () -> Unit,
    bookingViewModel: BookingViewModel
) {
    var selectedMethod by rememberSaveable{
        mutableStateOf("")
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PaymentDetailsTopBar(
                modifier = Modifier.fillMaxWidth(),
                onBackButtonClicked = {
                    onBackButtonClicked.invoke()
                },
                title = stringResource(id = R.string.select_payment_method)
            )
            Spacer(modifier = Modifier.height(20.dp))

            PaymentMethodsList(
                methods = listOf(
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
                onMethodSelected = { method ->
                    selectedMethod = method
                },
                selectedMethod = selectedMethod
            )
        }

        BooKingBottomBar(
            onBackButtonClicked = {
                onBackButtonClicked.invoke()
            },
            onNextButtonClicked = {
                val rand = (1..6).random()
                bookingViewModel.setBookingPrice(
                    price = "${rand}000"
                )
                onContinueClicked.invoke()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            isNextButtonEnabled = selectedMethod.isNotEmpty()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailsForm(

){

    var cardNumber by rememberSaveable{
        mutableStateOf("")
    }
    var expiryDate by rememberSaveable{
        mutableStateOf("")
    }
    var cvv by rememberSaveable{
        mutableStateOf("")
    }
    var country by rememberSaveable{
        mutableStateOf("Kenya")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = stringResource(id = R.string.card_information),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = cardNumber,
            onValueChange = {
                cardNumber = it
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.card_number),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.CreditCard,
                    contentDescription = "Card Number"
                )
            },
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = expiryDate,
                onValueChange = {
                    expiryDate = it
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.expiry_date),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
            )
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedTextField(
                value = cvv,
                onValueChange = {
                    cvv = it
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.cvc),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cvc),
                        contentDescription = "Card Number"
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.country_or_region),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(10.dp))
        CountrySelector(
            onCountrySelectionChange = {
                country = it
            },
            country = country
        )
    }
}



@Composable
fun PaymentMethodsList(
    modifier: Modifier = Modifier,
    methods: List<PaymentMethod>,
    onMethodSelected: (String) -> Unit,
    selectedMethod: String
){

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 10.dp)
    ) {
        items(methods) { method ->
            PaymentMethodItem(
                paymentMethod = method,
                selected = selectedMethod == method.title,
                onPaymentMethodSelected = {
                    onMethodSelected.invoke(method.title)
                }
            )
        }
    }


}

@Composable
fun PaymentMethodItem(
    modifier: Modifier = Modifier,
    paymentMethod: PaymentMethod,
    selected: Boolean,
    onPaymentMethodSelected: () -> Unit
){
    Card(
        modifier = modifier.padding(bottom = 10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
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
            Box(modifier = Modifier.weight(1f))
            RadioButton(
                selected = selected,
                onClick = { onPaymentMethodSelected.invoke() }
            )
        }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountrySelector(
    modifier: Modifier = Modifier,
    country: String = "",
    onCountrySelectionChange: (String)->Unit
){
    var expanded by remember { mutableStateOf(false) }
    val countries = listOf("Kenya", "India", "USA", "UK", "Canada", "Australia", "Japan", "China", "Russia", "France", "Germany", "Italy", "Spain", "Brazil", "Mexico", "Argentina", "Chile", "Peru", "Colombia", "South Africa", "Egypt", "Nigeria", "Ethiopia", "Morocco", "Tanzania", "Algeria", "Sudan", "Uganda", "Ghana", "Mozambique", "Angola", "Somalia", "Ivory Coast", "Madagascar", "Cameroon", "Niger", "Burkina Faso", "Mali", "Malawi", "Zambia", "Senegal", "Chad", "Zimbabwe", "Guinea", "Rwanda", "Benin", "Burundi", "Tunisia", "South Sudan", "Togo", "Sierra Leone", "Libya", "Congo", "Liberia", "Central African Republic", "Mauritania", "Eritrea", "Namibia", "Gambia", "Botswana", "Gabon", "Lesotho", "Guinea-Bissau", "Equatorial Guinea", "Mauritius", "Eswatini", "Djibouti", "Comoros", "Cabo Verde", "Sao Tome & Principe", "Seychelles")


    var textFieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded)
        Icons.Filled.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.ArrowDropDown


    Box(modifier = modifier) {
        CompositionLocalProvider(
            LocalTextInputService provides null
        ) {
            OutlinedTextField(
                value = country,
                onValueChange = { onCountrySelectionChange(it) },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .onFocusChanged {
                        if (it.isFocused) {
                            expanded = !expanded
                        }
                    }
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textFieldSize = coordinates.size.toSize()
                    },
                label = { Text(stringResource(id = R.string.gender)) },
                trailingIcon = {
                    Icon(icon,"contentDescription",
                        Modifier.clickable { expanded = !expanded })
                }
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textFieldSize.width.toDp()})
        ) {
            countries.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        onCountrySelectionChange(label)
                        expanded = false
                    },
                    text = {
                        Text(text = label)
                    }
                )
            }
        }
    }
}

data class PaymentMethod(
    val icon: ImageVector? = null,
    val title: String,
    val image: Painter? = null
)