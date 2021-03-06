package com.nemscep.muffin.setup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.balances.domain.entities.Balance.MainBalance
import com.nemscep.muffin.profile.domain.entities.Currency
import com.nemscep.muffin.profile.domain.entities.Profile
import com.nemscep.muffin.profile.domain.usecases.SetupProfile
import com.nemscep.muffin.setup.SetupEvents.NavigateToDashboard
import com.nemscep.muffin.setup.SetupEvents.SetupFailed
import kotlinx.coroutines.launch

class SetupViewModel(
    private val setupProfile: SetupProfile
) : ViewModel() {

    private val _events = MutableLiveData<SetupEvents>()
    val events: LiveData<SetupEvents> = _events

    fun setupProfile(
        name: String,
        monthlyIncome: Int,
        currency: Currency,
        currentBalance: Float,
        pin: Int
    ) {
        viewModelScope.launch {
            val profile = Profile(
                name = name,
                monthlyIncome = monthlyIncome,
                currency = currency
            )
            val mainBalance = MainBalance(
                value = currentBalance,
                currency = currency,
                isVisibleInOverview = true,
                id = 0 // TODO(Figure out a better way)
            )
            when (setupProfile(profile, pin, mainBalance)) {
                is Success -> _events.value = NavigateToDashboard
                is Failure -> _events.value = SetupFailed
            }
        }
    }
}

/**
 * Sealed class representing all side effects events which might occur inside profile setup flow.
 */
sealed class SetupEvents {
    object NavigateToDashboard : SetupEvents()
    object SetupFailed : SetupEvents()
}
