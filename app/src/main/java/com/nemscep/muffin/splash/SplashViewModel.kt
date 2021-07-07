package com.nemscep.muffin.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nemscep.muffin.profile.domain.usecases.IsLoggedIn

class SplashViewModel(
    isLoggedIn: IsLoggedIn
) : ViewModel() {
    val isLoggedIn = isLoggedIn().asLiveData()
}
