package com.nemscep.muffin.auth.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.auth.domain.entities.Pin
import com.nemscep.muffin.auth.domain.usecases.VerifyPin
import com.nemscep.muffin.auth.ui.AuthEvent.IncorrectPin
import com.nemscep.muffin.auth.ui.AuthEvent.ToDashboard
import kotlinx.coroutines.launch

class AuthViewModel(
    private val verifyPin: VerifyPin
) : ViewModel() {

    private val _events = MutableLiveData<AuthEvent>()
    val events = _events as LiveData<AuthEvent>

    fun authenticate(pin: Int) {
        viewModelScope.launch {
            when (verifyPin(Pin(pin))) {
                is Success -> _events.value = ToDashboard
                is Failure -> _events.value = IncorrectPin
            }
        }
    }
}

/**
 * Sealed class representing all authentication screen side effect events.
 */
sealed class AuthEvent {
    object ToDashboard : AuthEvent()
    object IncorrectPin : AuthEvent()
}
