package com.nemscep.muffin.track.ui.topup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemscep.burrito.Outcome.Failure
import com.nemscep.burrito.Outcome.Success
import com.nemscep.muffin.track.ui.topup.TrackTopupEvents.TrackingFailed
import com.nemscep.muffin.track.ui.topup.TrackTopupEvents.TrackingSuccessful
import com.nemscep.muffin.transactions.domain.entities.Transaction.Topup
import com.nemscep.muffin.transactions.domain.usecases.AddTransaction
import java.util.Date
import kotlinx.coroutines.launch

class TrackTopupViewModel(
    private val addTransaction: AddTransaction
) : ViewModel() {

    private val _events = MutableLiveData<TrackTopupEvents>()
    val events: LiveData<TrackTopupEvents> = _events

    fun trackTopup(amount: Float, date: Date, description: String) {
        viewModelScope.launch {
            val transaction = Topup(description = description, amount = amount, date = date)
            when (val outcome = addTransaction(transaction)) {
                is Success -> _events.value = TrackingSuccessful
                is Failure -> {
                    Log.e("Test", outcome.error.toString())
                    _events.value = TrackingFailed
                }
            }
        }
    }
}

/**
 * Sealed class representing all side effects inside topup screen.
 */
sealed class TrackTopupEvents {
    object TrackingSuccessful : TrackTopupEvents()
    object TrackingFailed : TrackTopupEvents()
}
