package com.nemscep.muffin.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nemscep.muffin.R
import com.nemscep.muffin.R.layout

class SplashFragment : Fragment(layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().navigate(R.id.dashboardFragment)
    }
}
