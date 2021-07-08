package com.nemscep.muffin.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nemscep.muffin.profile.domain.usecases.GetProfile

class OverviewViewModel(getProfile: GetProfile) : ViewModel() {
    val profile = getProfile().asLiveData()
}
