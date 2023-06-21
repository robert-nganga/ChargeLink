package com.nganga.robert.chargelink.ui.screens.authentication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.ui.viewmodels.AuthenticationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: AuthenticationViewModel
    onSubmitClicked:()->Unit
){
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LoginScreenTopAppBar(
            title = stringResource(id = R.string.login),
            onBackPressed = {},
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 15.dp)
        )
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(10.dp),
            shape = RoundedCornerShape(20.dp),
            border = null,
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                OutlinedTextField(
                    value = email,
                    label = {
                        Text(text = stringResource(id = R.string.email))
                    },
                    onValueChange = { email = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color(0x0D000000)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.MailOutline,
                            contentDescription = "Email Icon"
                        )
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(
                    value = password,
                    label = {
                        Text(text = stringResource(id = R.string.password))
                    },
                    onValueChange = { password = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color(0x0D000000)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = "password icom"
                        )
                    },
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            if (passwordVisible){
                                Icon(
                                    imageVector = Icons.Outlined.VisibilityOff,
                                    contentDescription = null
                                )
                            }else{
                                Icon(
                                    imageVector = Icons.Outlined.Visibility,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(
                            text = stringResource(id = R.string.sign_up),
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
                Button(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.align(Alignment.End),
                    onClick = { onSubmitClicked() }
                ) {
                    Text(text = stringResource(id = R.string.submit))
                }
            }
        }
    }

}

@Composable
fun LoginScreenTopAppBar(
    modifier: Modifier = Modifier,
    onBackPressed: ()->Unit,
    title: String

){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onBackPressed.invoke() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back"
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
    }
}
