package com.nemscep.muffin.track.ui.topup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.balances.domain.entities.Balance
import com.nemscep.muffin.balances.domain.usecases.GetBalances
import com.nemscep.muffin.track.ui.topup.TrackTopupEvents.TopupTrackingFailed
import com.nemscep.muffin.track.ui.topup.TrackTopupEvents.TopupTrackingSuccessful
import com.nemscep.muffin.transactions.domain.entities.Transaction.Topup
import com.nemscep.muffin.transactions.domain.usecases.AddTransaction
import java.util.Date
import kotlinx.coroutines.launch

class TrackTopupViewModel(
    private val addTransaction: AddTransaction,
    getBalances: GetBalances
) : ViewModel() {

    private val _events = MutableLiveData<TrackTopupEvents>()
    val events: LiveData<TrackTopupEvents> = _events

    val balances: LiveData<List<Balance>> = getBalances().asLiveData()

    fun trackTopup(amount: Float, date: Date, description: String, balance: Balance) {
        viewModelScope.launch {
            val transaction = Topup(description = description, amount = amount, date = date)
            when (addTransaction(transaction, balance)) {
                is Success -> _events.value = TopupTrackingSuccessful
                is Failure -> _events.value = TopupTrackingFailed
            }
        }
    }
}

/**
 * Sealed class representing all side effects inside topup screen.
 */
sealed class TrackTopupEvents {
    object TopupTrackingSuccessful : TrackTopupEvents()
    object TopupTrackingFailed : TrackTopupEvents()
}

