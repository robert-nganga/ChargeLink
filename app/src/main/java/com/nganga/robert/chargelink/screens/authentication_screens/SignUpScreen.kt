package com.nganga.robert.chargelink.screens.authentication_screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
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
import com.nganga.robert.chargelink.ui.components.ProgressDialog
import com.nganga.robert.chargelink.ui.viewmodels.AuthenticationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onSignUpClicked: () -> Unit,
    viewModel: AuthenticationViewModel,
    onNavigateToLogin: () -> Unit
){

    var state = viewModel.signUpState

    var confirmPassword by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    var confirmPasswordVisible by remember {
        mutableStateOf(false)
    }
    var passwordsMatch by remember{
        mutableStateOf(true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SignUpScreenTopAppBar(
            title = stringResource(id = R.string.sign_up),
            onBackPressed = {},
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
                    value = confirmPassword,
                    label = {
                        Text(text = stringResource(id = R.string.confirm_password))
                    },
                    onValueChange = { confirmPassword = it },
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
                    visualTransformation = if (confirmPasswordVisible)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            if (confirmPasswordVisible){
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
                Spacer(modifier = Modifier.height(10.dp))
                if (state.isError){
                    Text(
                        text = state.errorMsg,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (!passwordsMatch){
                    Text(
                        text = stringResource(id = R.string.passwords_dont_match),
                        color = Color.Red
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { onNavigateToLogin.invoke() }
                    ) {
                        Text(
                            text = stringResource(id = R.string.login),
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Button(
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            passwordsMatch = password == confirmPassword
                            if (passwordsMatch){
                                viewModel.onSignUpClicked(email, password)
                            }

                        }
                    ) {
                        Text(text = stringResource(id = R.string.submit))
                    }
                }

            }
        }
        LaunchedEffect(key1 = viewModel.hasUser){
            if (viewModel.hasUser){
                onSignUpClicked.invoke()
            }
        }
    }

}

@Composable
fun SignUpScreenTopAppBar(
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