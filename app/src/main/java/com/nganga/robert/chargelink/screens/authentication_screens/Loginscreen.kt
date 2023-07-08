package com.nganga.robert.chargelink.screens.authentication_screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.ui.components.ProgressDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: AuthenticationViewModel,
    onSubmitClicked:()->Unit,
    onNavigateToSignUp: ()->Unit,
    onLoginSuccessful: () -> Unit
){

    val state = viewModel.loginState
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var rememberMe by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 =state.isLoginSuccessful){
        if(state.isLoginSuccessful){
            onLoginSuccessful()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LoginScreenTopAppBar(
            title = stringResource(id = R.string.welcome_back),
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 15.dp)
        )
        if (state.isLoading) {
            ProgressDialog(
                text = stringResource(id = R.string.please_wait),
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
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
                        shape = RoundedCornerShape(10.dp),
                        value = email,
                        label = {
                            Text(
                                text = stringResource(id = R.string.email),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        onValueChange = { email = it },
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
                        shape = RoundedCornerShape(10.dp),
                        value = password,
                        label = {
                            Text(
                                text = stringResource(id = R.string.password),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        onValueChange = { password = it },
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
                    Spacer(modifier = Modifier.height(10.dp))
                    if (state.isError){
                        Text(
                            text = state.errorMsg,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = { rememberMe = it }
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(
                            text = stringResource(id = R.string.remember_me),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            viewModel.onLoginClicked(email, password)
                            if (state.isLoginSuccessful){
                                onSubmitClicked.invoke()
                            }
                        }
                    ) {
                        Text(text = stringResource(id = R.string.submit))
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.dont_have_an_account),
                    style = MaterialTheme.typography.bodyMedium
                )
                TextButton(
                    onClick = {
                        onNavigateToSignUp.invoke()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_up_here),
                        style = MaterialTheme.typography.bodyMedium,
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }


        LaunchedEffect(key1 = viewModel.hasUser){
            if (viewModel.hasUser){
                onLoginSuccessful()
            }
        }
    }
}

@Composable
fun LoginScreenTopAppBar(
    modifier: Modifier = Modifier,
    title: String
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
    }
}
