package com.nemscep.muffin.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nemscep.muffin.R.layout
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment(layout.fragment_splash) {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoggedIn.observe(viewLifecycleOwner) { handleLoggedInState(it) }
    }

    private fun handleLoggedInState(state: Boolean) {
        when (state) {
            false -> SplashFragmentDirections.actionGlobalAuthFragment()
            true -> SplashFragmentDirections.actionGlobalLoginFragment()
        }.also { destinationId -> findNavController().navigate(destinationId) }
    }
}
