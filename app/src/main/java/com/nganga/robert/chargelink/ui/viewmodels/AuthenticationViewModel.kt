package com.nganga.robert.chargelink.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.nganga.robert.chargelink.repository.AuthRepository

class AuthenticationViewModel(
    private val repository: AuthRepository = AuthRepository()
): ViewModel() {
}